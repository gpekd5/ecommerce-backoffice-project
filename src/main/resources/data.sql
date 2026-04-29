-- 관리자 테이블
INSERT INTO admins (name, email, password, phone, role, status, created_at, updated_at) VALUES
('슈퍼관리자', 'super@test.com', 'sparta1234', '010-1111-0000', 'SUPER', 'ACTIVE', NOW(), NOW()),
('박운영', 'op1@test.com', 'sparta1234', '010-1111-1111', 'OPERATION', 'ACTIVE', NOW(), NOW()),
('이관리', 'cs1@test.com', 'sparta1234', '010-1111-2222', 'CS', 'ACTIVE', NOW(), NOW());


-- 고객 테이블
INSERT INTO customers (name, email, phone, status, created_at, updated_at) VALUES
('강백호', 'kang@test.com', '010-2000-0001', 'ACTIVE', NOW(), NOW()),
('서태웅', 'seo@test.com', '010-2000-0002', 'ACTIVE', NOW(), NOW()),
('채치수', 'chae@test.com', '010-2000-0003', 'ACTIVE', NOW(), NOW()),
('정대만', 'jung@test.com', '010-2000-0004', 'ACTIVE', NOW(), NOW()),
('송태섭', 'song@test.com', '010-2000-0005', 'ACTIVE', NOW(), NOW()),
('윤대협', 'yoon@test.com', '010-2000-0006', 'ACTIVE', NOW(), NOW()),
('성현준', 'sung@test.com', '010-2000-0007', 'INACTIVE', NOW(), NOW()),
('김수겸', 'kim@test.com', '010-2000-0008', 'ACTIVE', NOW(), NOW()),
('이정환', 'lee@test.com', '010-2000-0009', 'ACTIVE', NOW(), NOW()),
('전호장', 'jeon@test.com', '010-2000-0010', 'ACTIVE', NOW(), NOW()),
('신준섭', 'shin@test.com', '010-2000-0011', 'ACTIVE', NOW(), NOW()),
('고민구', 'ko@test.com', '010-2000-0012', 'ACTIVE', NOW(), NOW()),
('남진', 'nam@test.com', '010-2000-0013', 'ACTIVE', NOW(), NOW()),
('허태환', 'heo@test.com', '010-2000-0014', 'SUSPENDED', NOW(), NOW()),
('안선생', 'ahn@test.com', '010-2000-0015', 'ACTIVE', NOW(), NOW()),
('박하진', 'park@test.com', '010-2000-0016', 'ACTIVE', NOW(), NOW()),
('이한나', 'hanna@test.com', '010-2000-0017', 'ACTIVE', NOW(), NOW()),
('유창수', 'yu@test.com', '010-2000-0018', 'INACTIVE', NOW(), NOW()),
('이재룡', 'ryong@test.com', '010-2000-0019', 'ACTIVE', NOW(), NOW()),
('김판석', 'pan@test.com', '010-2000-0020', 'ACTIVE', NOW(), NOW());


-- 상품 테이블
INSERT INTO products (name, category, price, stock, status, admin_id, created_at, updated_at) VALUES
('게이밍 마우스', 'ELECTRONICS', 85000, 50, 'AVAILABLE', 1, NOW(), NOW()),
('기계식 키보드', 'ELECTRONICS', 120000, 30, 'AVAILABLE', 1, NOW(), NOW()),
('4K 모니터', 'ELECTRONICS', 450000, 10, 'AVAILABLE', 2, NOW(), NOW()),
('자바 마스터 북', 'BOOKS', 35000, 100, 'AVAILABLE', 1, NOW(), NOW()),
('스프링 부트 가이드', 'BOOKS', 42000, 0, 'SOLD_OUT', 1, NOW(), NOW()),
('수분 크림', 'BEAUTY', 28000, 80, 'AVAILABLE', 2, NOW(), NOW()),
('선크림 SPF50', 'BEAUTY', 15000, 150, 'AVAILABLE', 2, NOW(), NOW()),
('오버핏 후드티', 'FASHION', 55000, 40, 'AVAILABLE', 1, NOW(), NOW()),
('데님 팬츠', 'FASHION', 49000, 0, 'DISCONTINUED', 1, NOW(), NOW()),
('유기농 사과 3kg', 'FOOD', 21000, 60, 'AVAILABLE', 2, NOW(), NOW()),
('냉동 닭가슴살', 'FOOD', 18000, 200, 'AVAILABLE', 2, NOW(), NOW()),
('포켓몬 인형', 'TOYS', 25000, 15, 'AVAILABLE', 1, NOW(), NOW()),
('무선 조종 자동차', 'TOYS', 98000, 5, 'AVAILABLE', 1, NOW(), NOW()),
('요가 매트', 'SPORTS', 32000, 25, 'AVAILABLE', 2, NOW(), NOW()),
('축구공 5호', 'SPORTS', 29000, 0, 'SOLD_OUT', 2, NOW(), NOW());


-- 주문 테이블
INSERT INTO orders (order_number, quantity, total_price, order_status, customer_id, product_id, admin_id, created_at, updated_at) VALUES
('ORD-2604-001', 1, 85000,  'DELIVERED', 1, 1, 1, NOW(), NOW()),
('ORD-2604-002', 1, 85000,  'DELIVERED', 2, 1, 1, NOW(), NOW()),
('ORD-2604-003', 1, 85000,  'DELIVERED', 3, 1, 1, NOW(), NOW()),
('ORD-2604-004', 1, 85000,  'DELIVERED', 4, 1, 1, NOW(), NOW()),
('ORD-2604-005', 1, 85000,  'DELIVERED', 5, 1, 1, NOW(), NOW()),
('ORD-2604-006', 1, 120000, 'DELIVERED', 6, 2, 1, NOW(), NOW()),
('ORD-2604-007', 1, 120000, 'DELIVERED', 7, 2, 1, NOW(), NOW()),
('ORD-2604-008', 1, 120000, 'DELIVERED', 8, 2, 1, NOW(), NOW()),
('ORD-2604-009', 1, 120000, 'DELIVERED', 9, 2, 1, NOW(), NOW()),
('ORD-2604-010', 1, 450000, 'DELIVERED', 10, 3, 2, NOW(), NOW()),
('ORD-2604-011', 1, 450000, 'DELIVERED', 11, 3, 2, NOW(), NOW()),
('ORD-2604-012', 1, 450000, 'DELIVERED', 12, 3, 2, NOW(), NOW()),
('ORD-2604-013', 1, 450000, 'DELIVERED', 13, 3, 2, NOW(), NOW()),
('ORD-2604-014', 1, 35000,  'DELIVERED', 14, 4, 1, NOW(), NOW()),
('ORD-2604-015', 1, 28000,  'DELIVERED', 15, 6, 2, NOW(), NOW()),
('ORD-2604-016', 1, 15000,  'DELIVERED', 16, 7, 2, NOW(), NOW()),
('ORD-2604-017', 2, 42000,  'DELIVERED', 17, 10, 2, NOW(), NOW()),
('ORD-2604-018', 1, 98000,  'DELIVERED', 18, 12, 1, NOW(), NOW());


-- 리뷰 테이블
INSERT INTO reviews (rating, comment, customer_id, product_id, order_id, created_at, updated_at) VALUES
(5, '정말 만족합니다! 배송도 빠르네요.', 1, 1, 1, NOW(), NOW()),
(4, '가성비 최고입니다. 추천해요.', 2, 1, 2, NOW(), NOW()),
(5, '생각보다 훨씬 퀄리티가 좋네요.', 3, 1, 3, NOW(), NOW()),
(3, '그냥 그래요. 가격만큼 합니다.', 4, 1, 4, NOW(), NOW()),
(5, '완전 대박! 하나 더 살까 고민 중입니다.', 5, 1, 5, NOW(), NOW()),
(1, '박스가 찌그러져서 왔어요. 기분 별로네요.', 6, 2, 6, NOW(), NOW()),
(5, '색상이 너무 영롱합니다. 실물 깡패!', 7, 2, 7, NOW(), NOW()),
(4, '마감 처리가 조금 아쉽지만 쓸만해요.', 8, 2, 8, NOW(), NOW()),
(5, '이 가격에 이 성능이라니.. 믿기지 않네요.', 9, 2, 9, NOW(), NOW()),
(5, '인생 템 등극입니다. 너무 좋아요.', 10, 3, 10, NOW(), NOW()),
(2, '소음이 좀 심한 편이네요.', 11, 3, 11, NOW(), NOW()),
(4, '디자인은 예쁜데 무게가 좀 나가요.', 12, 3, 12, NOW(), NOW()),
(5, '선물용으로 샀는데 받는 사람이 좋아하네요.', 13, 3, 13, NOW(), NOW()),
(3, '한 달 써보니 장단점이 확실합니다.', 14, 4, 14, NOW(), NOW()),
(5, '재구매 의사 200% 입니다.', 15, 6, 15, NOW(), NOW()),
(4, '사용법이 간단해서 어르신들도 좋아하세요.', 16, 7, 16, NOW(), NOW()),
(5, '배송 기사님이 친절하셔서 더 기분 좋네요.', 17, 10, 17, NOW(), NOW()),
(1, '사진이랑 너무 달라요. 실망입니다.', 18, 12, 18, NOW(), NOW());