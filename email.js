import http from "k6/http";
import { sleep } from "k6";

export const options = {
  scenarios: {
    periodic_burst: {
      executor: "ramping-arrival-rate",
      startRate: 1,
      timeUnit: "1s",
      preAllocatedVUs: 50,
      stages: [
        { target: 2, duration: "60s" }, // warmup low RPS
        { target: 0, duration: "65s" }, // idle → 스레드 제거
        { target: 5, duration: "60s" }, // 재생성 (2nd wave)
        { target: 0, duration: "65s" }, // 또 idle
        { target: 5, duration: "60s" }, // 3rd wave
        { target: 0, duration: "65s" },
        { target: 5, duration: "60s" },
        { target: 0, duration: "65s" },
      ],
    },
  },
};

export default function () {
  const payload = JSON.stringify({ email: "haechansong8@gmail.com" });

  const params = {
    headers: {
      "Content-Type": "application/json",
    },
  };

  http.post("https://gdgoctuk.com/api/emails", payload, params);
}
