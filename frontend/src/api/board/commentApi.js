import  apiClient  from "@/util/axiosConfig";

function addComment(param, success, fail) {
    apiClient.post(`/comment/add`, param ).then(success).catch(fail);
}

export {
    addComment,
}