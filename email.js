import http from "k6/http";
import { check, sleep } from "k6";

export const options = {
  scenarios: {
    batch1: {
      executor: "per-vu-iterations",
      vus: 10,
      iterations: 1,
      startTime: "0s",
      exec: "sendEmail",
    },
    batch2: {
      executor: "per-vu-iterations",
      vus: 10,
      iterations: 1,
      startTime: "5s",
      exec: "sendEmail",
    },
    batch3: {
      executor: "per-vu-iterations",
      vus: 10,
      iterations: 1,
      startTime: "10s",
      exec: "sendEmail",
    },
  },
};

export function sendEmail() {
  const url = "https://gdgoctuk.com/api/emails"; // 실제 API 주소
  const payload = JSON.stringify({
    email: "bukak2@tukorea.ac.kr",
  });

  const params = {
    headers: {
      "Content-Type": "application/json",
    },
  };

  const res = http.post(url, payload, params);
  check(res, {
    "status is 200": (r) => r.status === 200,
  });
}
