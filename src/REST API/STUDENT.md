# 👨‍🎓 Student API 문서

학생(Student) 관련 기능을 제공하는 REST API입니다.  
모든 API는 `/api/students` 경로를 기준으로 동작합니다.

---

## ✅ 엔드포인트 목록

| 메서드 | 경로                            | 설명                   | 요청 바디        | 응답 바디           |
|--------|----------------------------------|------------------------|------------------|----------------------|
| POST   | `/api/students`                 | 학생 생성               | ✅ 필요           | 생성된 학생 정보      |
| GET    | `/api/students`                 | 전체 학생 조회           | ❌ 없음           | 학생 리스트           |
| GET    | `/api/students/class/{classId}` | 특정 수업의 학생 목록 조회 | ❌ 없음           | 학생 리스트           |
| GET    | `/api/students/{id}`            | 특정 학생 조회           | ❌ 없음           | 학생 상세 정보        |
| PUT    | `/api/students/{id}`            | 학생 정보 수정           | ✅ 필요           | 수정된 학생 정보      |
| DELETE | `/api/students/{id}`            | 학생 삭제               | ❌ 없음           | 없음 (`200 OK` 응답)  |

---

## 📥 Request Body (StudentRequestDto)

```json
{
  "name": "김철수",
  "classId": 3,
  "school": "서울고등학교",
  "parentPhoneNumber": "010-1234-5678",
  "phoneNumber": "010-8765-4321",
  "email": "chulsoo@example.com",
  "age": 17
}
````

| 필드명               | 타입      | 설명        |
| ----------------- | ------- | --------- |
| name              | String  | 학생 이름     |
| classId           | Long    | 수업 ID     |
| school            | String  | 학교명       |
| parentPhoneNumber | String  | 보호자 전화번호  |
| phoneNumber       | String  | 학생 전화번호   |
| email             | String  | 학생 이메일 주소 |
| age               | Integer | 나이        |

---

## 📤 Response Body (StudentResponseDto)

```json
{
  "id": 12,
  "name": "김철수",
  "classId": 3,
  "school": "서울고등학교",
  "parentPhoneNumber": "010-1234-5678",
  "phoneNumber": "010-8765-4321",
  "email": "chulsoo@example.com",
  "age": 17
}
```

| 필드명               | 타입      | 설명        |
| ----------------- | ------- | --------- |
| id                | Long    | 학생 ID     |
| name              | String  | 학생 이름     |
| classId           | Long    | 수업 ID     |
| school            | String  | 학교명       |
| parentPhoneNumber | String  | 보호자 전화번호  |
| phoneNumber       | String  | 학생 전화번호   |
| email             | String  | 학생 이메일 주소 |
| age               | Integer | 나이        |

---

## 📌 예시

### 생성 예시 (POST `/api/students`)

**요청**

```json
{
  "name": "이영희",
  "classId": 4,
  "school": "한양여자고등학교",
  "parentPhoneNumber": "010-5555-1234",
  "phoneNumber": "010-3333-4444",
  "email": "younghee@example.com",
  "age": 16
}
```

**응답**

```json
{
  "id": 13,
  "name": "이영희",
  "classId": 4,
  "school": "한양여자고등학교",
  "parentPhoneNumber": "010-5555-1234",
  "phoneNumber": "010-3333-4444",
  "email": "younghee@example.com",
  "age": 16
}
```

---

### 수업별 학생 조회 예시 (GET `/api/students/class/4`)

**응답**

```json
[
  {
    "id": 13,
    "name": "이영희",
    "classId": 4,
    "school": "한양여자고등학교",
    "parentPhoneNumber": "010-5555-1234",
    "phoneNumber": "010-3333-4444",
    "email": "younghee@example.com",
    "age": 16
  },
  {
    "id": 14,
    "name": "박민수",
    "classId": 4,
    "school": "한양고등학교",
    "parentPhoneNumber": "010-7777-1111",
    "phoneNumber": "010-2222-3333",
    "email": "minsoo@example.com",
    "age": 17
  }
]
```

---

> ⛳ 참고:
>
> * 학생은 반드시 수업(`classId`)에 소속되어야 하며, 수정 시에도 이 정보는 유지됩니다.
> * `/api/students/class/{classId}`는 해당 수업에 소속된 전체 학생 목록을 반환합니다.

