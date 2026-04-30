package com.example.ecommercebackofficeproject.customer.service;

import com.example.ecommercebackofficeproject.customer.dto.request.*;
import com.example.ecommercebackofficeproject.customer.dto.response.*;
import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import com.example.ecommercebackofficeproject.global.exception.BadRequestException;
import com.example.ecommercebackofficeproject.global.exception.ConflictException;
import com.example.ecommercebackofficeproject.global.exception.NotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 고객 관리 비즈니스 로직을 처리하는 Service.
 *
 * 고객 목록 조회, 상세 조회, 정보 수정, 상태 변경, 삭제 기능을 제공한다.
 * 고객 목록 및 상세 조회 시 고객별 총 주문 수와 총 구매 금액도 함께 조회한다.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * 고객 목록을 조회한다.
     *
     * 검색 키워드, 고객 상태, 페이지 번호, 페이지 크기,
     * 정렬 기준, 정렬 방향을 기반으로 고객 목록을 조회한다.
     * 조회된 각 고객에 대해 총 주문 수와 총 구매 금액을 함께 계산하여 응답한다.
     *
     * @param request 고객 목록 조회 조건을 담은 요청 DTO
     * @return 고객 목록과 페이징 정보를 담은 응답 DTO
     */
    @Transactional(readOnly = true)
    public GetCustomerPageResponseDto getCustomers(GetCustomerRequestDto request) {
        CustomerStatus status = parseStatus(request.getStatus());

        Pageable pageable = createPageable(
                request.getPage(),
                request.getSize(),
                request.getSortBy(),
                request.getSortDir()
        );

        Specification<Customer> specification = createSearchSpecification(
                request.getKeyword(),
                status
        );

        Page<Customer> customerPage = customerRepository.findAll(specification, pageable);

        List<GetCustomerResponseDto> items = customerPage.getContent()
                .stream()
                .map(customer -> {
                    Long totalOrderCount = customerRepository.countOrdersByCustomerId(customer.getId());
                    Long totalOrderAmount = customerRepository.sumTotalOrderAmountByCustomerId(customer.getId());

                    return GetCustomerResponseDto.from(
                            customer,
                            totalOrderCount,
                            totalOrderAmount
                    );
                })
                .toList();

        return GetCustomerPageResponseDto.from(customerPage, items);
    }

    /**
     * 특정 고객의 상세 정보를 조회한다.
     *
     * 고객 ID를 기준으로 논리 삭제되지 않은 고객을 조회한다.
     * 고객 기본 정보와 함께 총 주문 수, 총 구매 금액을 응답한다.
     *
     * @param customerId 조회할 고객 ID
     * @return 고객 상세 정보를 담은 응답 DTO
     * @throws NotFoundException 고객이 존재하지 않거나 삭제 처리된 경우
     */
    @Transactional(readOnly = true)
    public GetCustomerDetailResponseDto getCustomer(Long customerId) {

        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));

        Long totalOrderCount = customerRepository.countOrdersByCustomerId(customer.getId());
        Long totalOrderAmount = customerRepository.sumTotalOrderAmountByCustomerId(customer.getId());

        return GetCustomerDetailResponseDto.from(
                customer,
                totalOrderCount,
                totalOrderAmount
        );
    }

    /**
     * 고객 정보를 수정한다.
     *
     * 고객 ID를 기준으로 논리 삭제되지 않은 고객을 조회한 뒤,
     * 이름, 이메일, 전화번호를 수정한다.
     * 이메일과 전화번호는 다른 고객과 중복될 수 없다.
     *
     * @param customerId 수정할 고객 ID
     * @param request 고객 정보 수정 요청 DTO
     * @return 수정된 고객 정보를 담은 응답 DTO
     * @throws NotFoundException 고객이 존재하지 않거나 삭제 처리된 경우
     * @throws ConflictException 이메일 또는 전화번호가 이미 사용 중인 경우
     */
    @Transactional
    public UpdateCustomerResponseDto updateCustomer(
            Long customerId,
            UpdateCustomerRequestDto request
    ) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));

        if (customerRepository.existsByEmailAndIdNot(request.getEmail(), customerId)) {
            throw new ConflictException("이미 사용 중인 이메일입니다.");
        }

        if (customerRepository.existsByPhoneAndIdNot(request.getPhone(), customerId)) {
            throw new ConflictException("이미 사용 중인 전화번호입니다.");
        }

        customer.updateInfo(
                request.getName(),
                request.getEmail(),
                request.getPhone()
        );

        return UpdateCustomerResponseDto.from(customer);
    }

    /**
     * 고객 상태를 변경한다.
     *
     * 요청으로 전달된 상태 문자열을 CustomerStatus enum으로 변환한 뒤,
     * 고객의 상태를 변경한다.
     *
     * @param customerId 상태를 변경할 고객 ID
     * @param request 고객 상태 변경 요청 DTO
     * @return 변경된 고객 상태 정보를 담은 응답 DTO
     * @throws NotFoundException 고객이 존재하지 않거나 삭제 처리된 경우
     * @throws BadRequestException 유효하지 않은 고객 상태가 전달된 경우
     */
    @Transactional
    public UpdateCustomerStatusResponseDto updateCustomerStatus(
            Long customerId,
            UpdateCustomerStatusRequestDto request
    ) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));

        CustomerStatus newStatus = parseStatus(request.getStatus());

        if (newStatus == null) {
            throw new BadRequestException("유효하지 않은 상태입니다.");
        }

        customer.changeStatus(newStatus);

        return UpdateCustomerStatusResponseDto.from(customer);
    }

    /**
     * 고객을 삭제 처리한다.
     *
     * 고객 ID를 기준으로 논리 삭제되지 않은 고객을 조회한 뒤,
     * BaseEntity의 delete 메서드를 사용하여 deletedAt 값을 설정한다.
     * 실제 DB 데이터는 삭제하지 않는다.
     *
     * @param customerId 삭제할 고객 ID
     * @throws NotFoundException 고객이 존재하지 않거나 이미 삭제 처리된 경우
     */
    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));

        customer.delete();
    }

    /**
     * 고객 검색 조건을 생성한다.
     *
     * Specification을 사용하여 동적 검색 조건을 생성한다.
     * 기본적으로 deletedAt이 null인 고객만 조회한다.
     * keyword가 있으면 고객 이름 또는 이메일에 포함되는 고객을 조회하고,
     * status가 있으면 해당 상태의 고객만 조회한다.
     *
     * @param keyword 검색 키워드
     * @param status 고객 상태 필터
     * @return 고객 검색 조건 Specification
     */
    private Specification<Customer> createSearchSpecification(
            String keyword,
            CustomerStatus status
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));

            if (keyword != null && !keyword.isBlank()) {
                Predicate nameLike = criteriaBuilder.like(
                        root.get("name"),
                        "%" + keyword + "%"
                );

                Predicate emailLike = criteriaBuilder.like(
                        root.get("email"),
                        "%" + keyword + "%"
                );

                predicates.add(criteriaBuilder.or(nameLike, emailLike));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 고객 상태 문자열을 CustomerStatus enum으로 변환한다.
     *
     * status 값이 null이거나 공백이면 필터 조건에서 제외하기 위해 null을 반환한다.
     * 유효하지 않은 상태 문자열이 전달되면 BadRequestException을 발생시킨다.
     *
     * @param status 변환할 고객 상태 문자열
     * @return 변환된 CustomerStatus enum 값
     * @throws BadRequestException 유효하지 않은 고객 상태가 전달된 경우
     */
    private CustomerStatus parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }

        try {
            return CustomerStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("유효하지 않은 고객 상태입니다.");
        }
    }

    /**
     * Pageable 객체를 생성한다.
     *
     * 클라이언트로부터 전달받은 page 값은 1부터 시작하지만,
     * Spring Data JPA의 페이지 번호는 0부터 시작하므로 page - 1을 적용한다.
     * 정렬 기준과 정렬 방향도 함께 적용한다.
     *
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param sortBy 정렬 기준 필드
     * @param sortDir 정렬 방향
     * @return 페이징 및 정렬 조건을 담은 Pageable 객체
     * @throws BadRequestException 페이지 번호 또는 페이지 크기가 유효하지 않은 경우
     */
    private Pageable createPageable(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        if (page < 1) {
            throw new BadRequestException("페이지 번호는 1 이상이어야 합니다.");
        }

        if (size < 1) {
            throw new BadRequestException("페이지 크기는 1 이상이어야 합니다.");
        }

        String validatedSortBy = validateSortBy(sortBy);

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return PageRequest.of(
                page - 1,
                size,
                Sort.by(direction, validatedSortBy)
        );
    }

    /**
     * 정렬 기준 필드를 검증한다.
     *
     * 허용된 정렬 기준만 사용할 수 있도록 제한한다.
     * sortBy 값이 null이거나 공백이면 기본 정렬 기준인 createdAt을 반환한다.
     *
     * @param sortBy 검증할 정렬 기준 필드
     * @return 검증된 정렬 기준 필드
     * @throws BadRequestException 허용되지 않은 정렬 기준이 전달된 경우
     */
    private String validateSortBy(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return "createdAt";
        }

        return switch (sortBy) {
            case "name", "email", "createdAt" -> sortBy;
            default -> throw new BadRequestException("유효하지 않은 정렬 기준입니다.");
        };
    }
}