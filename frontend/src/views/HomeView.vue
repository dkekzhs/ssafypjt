<script setup>
import { ref, onMounted, watch, computed } from "vue";
import KakaoMap from "../components/layout/KakaoMap.vue";
import TravelList from "@/components/travel/TravelList.vue";
import Rating from "@/components/travel/Rating.vue";
import VSelect from "@/components/common/VSelect.vue";
import { getSido, getType, getGugun, getTravelSite } from "@/util/travel/travelApi";
import VCheckbox from "../components/common/VCheckbox.vue";
import { useMenuStore, logout } from "@/stores/menu";
import PlanModal from "@/components/modal/PlanModal.vue";
import ChatModal from "@/components/modal/ChatModal.vue";
import { vaild } from "@/api/chat/chatApi";
import { useSocketStore } from "@/api/chat/socket";

const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;
const menuStore = useMenuStore();

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const optionList = ref([
  { text: "전체", value: 0, checked: false },
  { text: "여행지", value: 12, checked: false },
  { text: "밥", value: 14, checked: false },
  { text: "놀거리", value: 15, checked: false },
]);
const chargingStations = ref([]);
const selectStation = ref({});
const selectSido = ref(0);
const selectGugun = ref(0);
const selectOption = ref([]);

const messages = ref([]);

const socketStore = useSocketStore();



onMounted(() => {
  // getChargingStations();
  getSidoList();
});

const getSidoList = () => {
  getSido(
    ({ data }) => {
      let options = [];
      options.push({ text: "시도선택", value: "" });
      data.list.forEach((sido) => {
        options.push({ text: sido.sido_name, value: sido.sido_code });
      });
      sidoList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

const getChargingStations = () => {
  getTravelSite(
    {
      sidoCode: selectSido.value,
      gugunCode: selectGugun.value,
    },
    ({ data }) => {
      chargingStations.value = data.list;
    },
    (err) => {
      console.log(err);
    }
  );
};

const onChangeSido = (val) => {
  getGugun(
    { sidoCode: val },
    ({ data }) => {
      let options = [];
      options.push({ text: "구군선택", value: "" });
      data.list.forEach((gugun) => {
        options.push({ text: gugun.gugun_name, value: gugun.gugun_code });
      });
      gugunList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );

  selectSido.value = val;
};

const onChangeGugun = (val) => {
  selectGugun.value = val;
  getChargingStations();
};

const onChangeOption = (val) => {
  selectOption.value = val;
  for (let option of optionList.value) {
    option.checked = !option.checked;
  }
  console.log(selectOption.value);
};

const viewStation = (station) => {
  selectStation.value = station;
};

function openChatModal() {
  document.getElementById("chat_modal").style.display = "flex";
}
function openModal() {
  document.getElementById("modal").style.display = "flex";
}


async function connectSocketChat() {
  await socketStore.connect("/chat", socketStore.handlePacket);
  socketStore.sendMessage({ type: "getPlanList" });
}
function check() {
  vaild(
    (res) => {
      console.log(res);
      if (res.data.message === "로그인해주세요") {
        alert("로그인해주세요")
        logout();
        return;
      }
      if (res.data.message === "유저 채팅방 입장 성공"  && !socketStore.isConnected) {
        openChatModal();
        connectSocketChat();
      } else if (socketStore.isConnected) {
        openChatModal();
        //소켓연결되어있다. 채팅방에 입장
      } else {
        openModal();
        //없으면 계획등록
      }
    },
    (err) => {
      console.log(err);
    }
  );
}
const destinations = computed(() => socketStore.getDestinations);
</script>

<template>
  <h1>홈 화면입니다.</h1>
  <Rating />
  <v-row class="v-row">
    <v-col class="v-col-4">
      <VSelect class="v-text-field" :selectOption="sidoList" @onKeySelect="onChangeSido" />
      <VSelect :selectOption="gugunList" @onKeySelect="onChangeGugun" />
      <VCheckbox :selectOption="optionList" @onKeySelect="onChangeOption" />
      <TravelList :destinations="destinations" />

      <!-- 모달 버튼-->
      <template v-if="menuStore.login" class="asdf">
        <v-btn class="asdfbtn" icon="mdi-plus" size="small" @click="check"></v-btn>

        <div id="modal" class="modal" @click.self="closeModal">
          <div class="modal-content">
            <PlanModal />
          </div>
        </div>
        <div id="chat_modal" class="modal" @click.self="closeModal">
          <div class="modal-content"><ChatModal :messages="messages" /></div>
        </div>
      </template>
    </v-col>

    <v-col class="v-col-8">
      <KakaoMap
        :stations="chargingStations"
        :selectStation="selectStation"
        :destinations="destinations"
      />
    </v-col>
  </v-row>

  <!-- 아래 출력-->
  <div class="container text-center mt-3">
    <v-data-table
      :headers="headers"
      :items="chargingStations"
      item-key="zipcode"
      @click="viewStation"
    >
      <thead>
        <tr class="text-center">
          <th scope="col">이름</th>
          <th scope="col">주소</th>
          <th scope="col">상세주소</th>
          <th scope="col">전화번호</th>
          <th scope="col">위도</th>
          <th scope="col">경도</th>
        </tr>
      </thead>
      <tbody>
        <tr
          class="text-center"
          v-for="station in chargingStations"
          :key="station.zipcode"
          @click="viewStation(station)"
        >
          <th>{{ station.title }}</th>
          <td>{{ station.addr1 }}</td>
          <td>{{ station.addr2 }}</td>
          <td>{{ station.tel }}</td>
          <td>{{ station.latitude }}</td>
          <td>{{ station.longitude }}</td>
        </tr>
      </tbody>
    </v-data-table>
  </div>
</template>

<style scoped>
.modal {
  border: 10px solid #f00;
  left: calc(50%);
  z-index: 100;
  display: none;
  position: fixed;
  top: 20%;
  width: auto;
  height: auto;
  background-color: rgba(0, 0, 0, 0.5);
  align-items: baseline;
  justify-content: flex-start;
}
.modal-content {
  background-color: #fff;
  padding: 20px;
  width: 80%;
  margin: 20px auto;
}
.asdfbtn {
  position: fixed;
  bottom: 160px;
  left: 50px;
}

.fdsabtn {
  position: fixed;
  bottom: 200px;
  left: 50px;
}
</style>
