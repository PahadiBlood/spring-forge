import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '30s', target: 5 },  // 1. Warm up (Safe)
    { duration: '1m', target: 3 }, // 2. Cross the 200 Tomcat thread limit
    { duration: '1m', target: 7 },// 3. Push toward the breaking point
    { duration: '30s', target: 2 },  // 4. Cool down
  ],
};


export default function () {
    // math.random generates number between 0 to 0.99
    // floor removes any value after . means 0.99 in this it will become 0
    //
    const sleepTime = Math.floor((Math.random() * 10)) *1000;

    const url = `http://localhost:8001/api/v1/orders/notifications?sleeptime=${sleepTime}`;

      const res = http.get(url);

      check(res, {
        'is 200': (r) => r.status === 200,
      });

      sleep(1);
}