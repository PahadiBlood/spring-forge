import http from 'k6/http';
import { SharedArray } from 'k6/data';
import { check, sleep } from 'k6';

const userData = new SharedArray('user data', function () {
  return JSON.parse(open('./users.json'));
});

export const options = {
  stages: [
    { duration: '30s', target: 50 },  // 1. Warm up (Safe)
    { duration: '1m', target: 250 }, // 2. Cross the 200 Tomcat thread limit
    { duration: '1m', target: 1000 },// 3. Push toward the breaking point
    { duration: '30s', target: 0 },  // 4. Cool down
  ],
};

export default function () {
  const user = userData[Math.floor(Math.random() * userData.length)];
  const url = `http://localhost:8001/api/v1/orders?userId=${user.userId}&pageNo=${user.pageNo}&size=${user.size}`;

  const res = http.get(url);

  check(res, {
    'is 200': (r) => r.status === 200,
  });

  sleep(1); 
}
