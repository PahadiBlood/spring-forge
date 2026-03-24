import http from 'k6/http';
import { SharedArray } from 'k6/data';

// Load the JSON file into memory
const data = new SharedArray('order ids', function () {
  return JSON.parse(open('./data.json'));
});

export const options = { vus: 5, duration: '10s' };

export default function () {
  // Pick a random ID from the list
  const order = data[Math.floor(Math.random() * data.length)];
  const url = `http://localhost:8001/api/v1/orders?orderId=${order.id}&orderStatus=PACKED`;
  
  http.patch(url);
}