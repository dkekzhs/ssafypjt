import apiClient from "@/util/axiosConfig";

function getSido(success, fail) {
  apiClient.post("/travel/search/getSidoList").then(success).catch(fail);
}

function getType(success, fail) {
  apiClient.post("/travel/search/getTypeList").then(success).catch(fail);
}

function getGugun(body, success, fail) {
  apiClient.post("/travel/search/getGugunList", body).then(success).catch(fail);
}

function getTravelSite(body, success, fail) {
  apiClient.post("/travel/search/travel", body).then(success).catch(fail);
}

export { getSido, getType, getGugun, getTravelSite };
