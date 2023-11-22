import apiClient from "@/util/axiosConfig";

function vaild(success, fail) {
  apiClient.post(`/room/isValid`).then(success).catch(fail);
}

export { vaild };
