# Shopping-basket-server

## 장바구니 어플리케이션 백엔드 어플리케이션

기능 설명

주문 생성 POST /api/v1/orders
```
REQUEST BODY EXAMPLE
{
  "voucherId":["2686fe33-a829-43af-9404-ff6f6151db69"],
  "email":"j05018@naver.com",
  "address":"서울시 강남구",
  "postcode":"12345",
  "orderItems":[
      {
          "productId":"cb65a4fc-6b32-499b-a662-a5431633ebbe",
          "price":10000,
          "quantity":10
      },
      ...
  ]
}
```
```
RESPONSE BODY EXAMPLE
{
  "orderId":"a98b788f-3128-4c2d-aaae-59a481d8970d",
  "voucherId":"2686fe33-a829-43af-9404-ff6f6151db69",
  "email":"j05018@naver.com",
  "address":"ìì¸ì ê°ë¨êµ¬",
  "postcode":"12345",
  "orderItems":   [
      {
          "productId":"cb65a4fc-6b32-499b-a662-a5431633ebbe",
          "price":10000,
          "quantity":10
      },
      ....
  ],
  "createdAt":"2022-05-10T09:48:34.191819",
  "updatedAt":"2022-05-10T09:48:34.191837"
}
```

주문 내역 조회 GET /api/v1/orders
```
RESPONSE BODY EXAMPLE
[
    {
        "orderId": "54709343-05fd-4f00-b0a6-0db713d9349a",
        "voucherId": "151ea6aa-cc20-11ec-81eb-e36c70f06219",
        "email": "jk05018@pusan.ac.kr",
        "address": "asdf",
        "postcode": "afsd",
        "orderItems": [
            {
                "productId": "de2327f2-cc1f-11ec-81eb-e36c70f06219",
                "price": 2000,
                "quantity": 2
            },
            {
                "productId": "decba29c-cc1f-11ec-81eb-e36c70f06219",
                "price": 3000,
                "quantity": 1
            },
            {
                "productId": "df4f82ce-cc1f-11ec-81eb-e36c70f06219",
                "price": 1000,
                "quantity": 1
            }
        ],
        "createdAt": "2022-05-10T09:59:04.15838",
        "updatedAt": "2022-05-10T09:59:04.158484"
    },
	….
   
]
```

주문정보(email, address, postcode)  수정 POST /api/v1/orders/{orderId}
```
REQUEST BODY EXAMPLE
{
    "email":"j05018@naver.com",
    "address":"서울시 강남구",
    "postcode":"12345"
}

```

```
RESPONSE BODY EXAMPLE
 {
        "orderId": "54709343-05fd-4f00-b0a6-0db713d9349a",
        "voucherId": "151ea6aa-cc20-11ec-81eb-e36c70f06219",
        "email": "j05018@naver.com",
        "address": "서울시 강남구",
        "postcode": "12345",
        "orderItems": [
            {
                "productId": "de2327f2-cc1f-11ec-81eb-e36c70f06219",
                "price": 2000,
                "quantity": 2
            },
            {
                "productId": "decba29c-cc1f-11ec-81eb-e36c70f06219",
                "price": 3000,
                "quantity": 1
            },
            {
                "productId": "df4f82ce-cc1f-11ec-81eb-e36c70f06219",
                "price": 1000,
                "quantity": 1
            }
        ],
        "createdAt": "2022-05-10T09:59:04.15838",
        "updatedAt": "2022-05-10T09:59:04.158484"
    },

```

주무 삭제  DELETE /api/v1/orders/{orderId}

내부에서 에러 발새 시 동일하 포맷 반환
```
RESPONSE BODY EXAMPLE
{
    "errorCode": "E0001",
    "errorMessage": "Required request body is missing"
}
```
