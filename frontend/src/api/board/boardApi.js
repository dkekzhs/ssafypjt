import  apiClient  from "@/util/axiosConfig";


function listArticle(param, success, fail) {
    apiClient.get(`/board/list`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
    console.log(articleno)
    apiClient.get(`/board/view/${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("boardjs article", article);
  apiClient.post(`/board/write`, article).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {
    apiClient.get(`/board/modify/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
    apiClient.put(`/board`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
    apiClient.delete(`/board/${articleno}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
};