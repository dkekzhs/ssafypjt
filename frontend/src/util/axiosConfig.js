import axios from "axios";

axios.defaults.withCredentials = true; // withCredentials 전역 설정

const apiClient = axios.create({
  baseURL: "http://70.12.107.143:80/enjoytrip",
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});
export default apiClient;
