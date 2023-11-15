import axios from "axios";

axios.defaults.withCredentials = true; // withCredentials 전역 설정

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_VUE_API_URL,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});
export default apiClient;
