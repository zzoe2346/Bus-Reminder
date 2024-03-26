## 버스 알리미 API REFERENCE
> 이 API를 활용한 웹사이트 https://zzoe2346.github.io/
### 소개
이 API는 클라이언트에서 요청이 들어오면, 지정된 버스 도착 정보가 도착할 때까지 외부 API(버스 도착 정보api)를 반복적으로 호출합니다. 목표한 데이터를 획득하면, 푸시 알림을 요청합니다.

### GET: 버스 알림 요청 (main service)
> 특정 버스정류장에서 특정 버스가 특정 버스정류장 전에 알림을 하도록 요청
#### URL
 /bus-arrival-info
#### Method
GET
#### Parameters

1. **nodeId** : 버스 정류장의 고유 식별자.
2. **targetNumber** : 확인하려는 버스의 차량 번호.
3. **targetBus** : 확인하려는 버스의 노선 정보.
4. **userId** : 요청한 유저 식별자

#### Sample
```javascript
GET localhost:8080/bus-arrival-info?nodeId=DGB7031010800&targetNumber=5&targetBus=156&userId=zzoe2346
```
### GET: 취소 요청
> 요청한 버스 알림을 취소하는 요청
#### URL
/cancle
#### Method
GET
#### Parameters
1. **userId** : 요청한 유저 식별자. 버스 알림을 요청한 userId와 같아야 한다.

#### Sample
```javascript
GET localhost:8080/cancel?userId=zzoe2346
```