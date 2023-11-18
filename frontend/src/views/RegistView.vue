<script setup>

import { ref } from "vue";
import { regist } from "@/stores/menu";
const user = ref({
	"user_id": "",
	"user_name": "",
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
					<input v-model="user.user_id" type="text" placeholder="아이디" /> 
					<input v-model="user.user_name" type="text" placeholder="이름 입력" />
					<input v-model="user.user_password" type="password" placeholder="비밀번호" />
					<input  v-model="user.age" type="text" placeholder="나이" /> 
					<button @click="regist(user)">회원가입</button>
			</div>
		</div>
	</div>
s</template>
<style scoped>

</style>