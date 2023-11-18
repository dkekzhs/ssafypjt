<script setup>

import { ref } from "vue";
import { regist } from "@/stores/menu";
const user = ref({
    "user_id": "",
    "user_password": "",
    "email_id": "",
    "email_domain": "",
    "age" : 0 
})


</script>

<template>
	<div id="signupModal" class="modalWrap">
		<div id="modalBody" class="modalBody">
			<span id="closeSignupBtn" class="closeBtn">&times;</span>
			<div class="form">
					<input v-mdoel="user.user_id" id="id" name="id" type="text" placeholder="아이디" /> 
					<input v-mdoel="user.user_password" id="password" name="password" type="password" placeholder="비밀번호" />
					<input  v-mdoel="user.age" id="rrn" name="rrn" type="text" placeholder="나이" /> 
					<button @onclick="regist(user.value)" type="button">회원 등록</button>
			</div>
		</div>
	</div>
s</template>
<style scoped>

</style>