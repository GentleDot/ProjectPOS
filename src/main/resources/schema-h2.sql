DROP TABLE IF EXISTS goods;

CREATE TABLE goods(
	goods_no BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '상품순번',
	goods_code VARCHAR(20) NOT NULL UNIQUE COMMENT '상품코드',
	goods_name VARCHAR(50) NOT NULL COMMENT '상품명',
    goods_price INT NOT NULL COMMENT '상품금액',
    goods_category VARCHAR(4) NOT NULL COMMENT '상품카테고리',
    goods_description VARCHAR(300) COMMENT '상품설명',
    status_for_sale TINYINT NOT NULL DEFAULT TRUE COMMENT '상품판매여부',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '등록일시'
);