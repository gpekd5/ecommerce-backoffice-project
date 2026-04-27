package com.example.ecommercebackofficeproject.customer.service;

import com.example.ecommercebackofficeproject.customer.dto.request.*;
import com.example.ecommercebackofficeproject.customer.dto.response.*;
import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    /*
     * 고객 목록 조회
     *
     * 검색 키워드, 상태 필터, 페이지 번호, 페이지 크기,
     * 정렬 기준, 정렬 방향을 기반으로 고객 목록을 조회한다.
     *
     * 추가로 각 고객별 총 주문 수와 총 구매 금액을 함께 조회하여
     * 응답 DTO에 포함한다.
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



    /*
     * 고객 상세 조회
     *
     * 고객 ID를 기준으로 고객 정보를 조회한다.
     * 삭제 처리된 고객은 조회하지 않는다.
     *
     * 고객 기본 정보와 함께 총 주문 수, 총 구매 금액을 응답한다.
     */
    @Transactional(readOnly = true)
    public GetCustomerDetailResponseDto getCustomer(Long customerId) {

        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));

        Long totalOrderCount = customerRepository.countOrdersByCustomerId(customer.getId());
        Long totalOrderAmount = customerRepository.sumTotalOrderAmountByCustomerId(customer.getId());

        return GetCustomerDetailResponseDto.from(
                customer,
                totalOrderCount,
                totalOrderAmount
        );
    }

    /*
     * 고객 정보 수정
     *
     * 고객 ID를 기준으로 고객을 조회한 뒤,
     * 이름, 이메일, 전화번호를 수정한다.
     *
     * 실제 필드 변경은 Entity의 updateInfo 메서드를 통해 수행한다.
     */
    @Transactional
    public UpdateCustomerResponseDto updateCustomer(
            Long customerId,
            UpdateCustomerRequestDto request
    ) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));

        if (customerRepository.existsByEmailAndIdNot(request.getEmail(), customerId)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (customerRepository.existsByPhoneAndIdNot(request.getPhone(), customerId)) {
            throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
        }

        customer.updateInfo(
                request.getName(),
                request.getEmail(),
                request.getPhone()
        );

        return UpdateCustomerResponseDto.from(customer);
    }

    /*
     * 고객 상태 변경
     *
     * 요청으로 받은 상태 문자열을 CustomerStatus enum으로 변환한 뒤
     * 고객 상태를 변경한다.
     */
    @Transactional
    public UpdateCustomerStatusResponseDto updateCustomerStatus(
            Long customerId,
            UpdateCustomerStatusRequestDto request
    ) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));

        CustomerStatus newStatus = parseStatus(request.getStatus());

        if (newStatus == null) {
            throw new IllegalArgumentException("유효하지 않은 상태입니다.");
        }

        customer.changeStatus(newStatus);

        return UpdateCustomerStatusResponseDto.from(customer);
    }

    /*
     * 고객 삭제
     *
     * 고객 ID를 기준으로 고객을 조회한 뒤 삭제 처리한다.
     *
     * 실제 DB 데이터를 삭제하지 않고 deletedAt 값을 설정하는
     * 논리 삭제 방식으로 처리한다.
     */
    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));

        customer.delete();
    }


    /*
     * 고객 검색 조건 생성
     *
     * Specification을 사용하여 동적 검색 조건을 만든다.
     *
     * 조건:
     * - deletedAt이 null인 고객만 조회
     * - keyword가 있으면 이름 또는 이메일에 포함되는 고객 조회
     * - status가 있으면 해당 상태의 고객만 조회
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

    /*
     * 고객 상태 문자열을 Enum으로 변환
     *
     * status 값이 없으면 null을 반환하여 필터 조건에서 제외한다.
     * 유효하지 않은 상태값이면 예외를 발생시킨다.
     */
    private CustomerStatus parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }

        try {
            return CustomerStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 고객 상태입니다.");
        }
    }

    /*
     * Pageable 객체 생성
     *
     * page는 사용자에게 1부터 입력받지만,
     * Spring Data JPA는 0부터 시작하므로 page - 1을 적용한다.
     *
     * 정렬 기준과 정렬 방향을 함께 적용한다.
     */
    private Pageable createPageable(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        if (page < 1) {
            throw new IllegalArgumentException("페이지 번호는 1 이상이어야 합니다.");
        }

        if (size < 1) {
            throw new IllegalArgumentException("페이지 크기는 1 이상이어야 합니다.");
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

    /*
     * 정렬 기준 검증
     *
     * 허용된 정렬 기준만 사용하도록 제한한다.
     * 잘못된 필드명으로 정렬 요청이 들어오는 것을 방지한다.
     */
    private String validateSortBy(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return "createdAt";
        }

        return switch (sortBy) {
            case "name", "email", "createdAt" -> sortBy;
            default -> throw new IllegalArgumentException("유효하지 않은 정렬 기준입니다.");
        };
    }
}
