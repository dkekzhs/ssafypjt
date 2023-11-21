import axios from "axios";

axios.defaults.withCredentials = true; // withCredentials 전역 설정

const apiClient = axios.create({
  baseURL: "http://localhost:80/enjoytrip",
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});
export default apiClient;
