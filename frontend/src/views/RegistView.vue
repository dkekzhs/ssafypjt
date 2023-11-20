<script setup>

import { ref } from "vue";
import { regist } from "@/stores/menu";
import VSelect from "@/components/common/VSelect.vue";

const user = ref({
	"user_id": "",
	"user_name": "",
    "user_password": "",
    "email_id": "",
    "email_domain": "",
    "age" : 0 
})
const selectOption = ref([
  { text: "도메인설정", value: "" },
  { text: "네이버", value: "naver.com" },
  { text: "구글", value: "google.com" },

]);

const changeKey = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  user.domain = val;
};

</script>

<template>
	<v-container>
	  <v-card>
		<v-row>
		  <v-col cols="12">
			<div class="form">
			  <v-text-field v-model="user.user_id" label="아이디" outlined></v-text-field>
			  <v-text-field v-model="user.user_name" label="이름 입력" outlined></v-text-field>
			  <v-text-field v-model="user.user_password" label="비밀번호" type="password" outlined></v-text-field>
			  <v-text-field v-model="user.age" label="나이" outlined></v-text-field>
  
			  <v-row>
				<v-col cols="6">
				  <v-text-field v-model="user.email_id" label="이메일" outlined></v-text-field>
				</v-col>
				<v-col cols="6">
					<VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
				</v-col>
			  </v-row>
  
			  <v-btn @click="regist(user)">회원가입</v-btn>
			</div>
		  </v-col>
		</v-row>
	  </v-card>
	</v-container>
  </template>
<style scoped>

</style>