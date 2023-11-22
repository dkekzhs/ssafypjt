<script setup>
import { ref, onMounted, watch } from "vue";
import KakaoMap from "../components/layout/KakaoMap.vue";
import TravelList from "@/components/travel/TravelList.vue";
import Rating from "@/components/travel/Rating.vue";
import VSelect from "@/components/common/VSelect.vue";
import { getSido, getType, getGugun, getTravelSite } from "@/util/travel/travelApi";
import VCheckbox from "../components/common/VCheckbox.vue";
import { useMenuStore } from "@/stores/menu";
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
  { text: "ㅁㄴㅇㄹ", value: 12, checked: false },
  { text: "ㅂㅈㄷㄱ", value: 14, checked: false },
  { text: "ㅋㅌㅊㅍ", value: 15, checked: false },
]);
const chargingStations = ref([]);
const selectStation = ref({});
const selectSido = ref(0);
const selectGugun = ref(0);
const selectOption = ref([]);
const selectAll = ref(false);
const isModalOpen = ref(false);
const isConnected = ref(false);
const destinations = ref([
  {
    title: "파리",
    order: 1,
    image: "paris.jpg",
    addr1: "빛의 도시",
  },
  {
    title: "도쿄",
    order: 2,
    image: "tokyo.jpg",
    addr1: "모던하고 활기찬 도시",
  },
  // 필요한 만큼 여행지를 추가할 수 있습니다.
]);
const socketStore = useSocketStore();

const addDestination = (destination) => {
  destinations.value.push(destination);
};

const param = ref({
  serviceKey: VITE_OPEN_API_SERVICE_KEY,
  pageNo: 1,
  numOfRows: 20,
  zscode: 0,
});

watch(destinations.forEach, (newDestinations, oldDestinations) => {
  console.log("Destinations changed!");
  console.log("New Destinations:", newDestinations);
  console.log("Old Destinations:", oldDestinations);
  // 원하는 로직을 추가하세요.
});

watch(
  () => destinations.value,
  () => {
    console.log("Destinations changed!");
    destinations.value.forEach((destination) => console.log("New Destination:", destination));
  },
  { deep: true }
);

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
  // param.value.zscode = val;
  selectGugun.value = val;
  getChargingStations();
};

const onChangeOption = (val) => {
  /*
  if (val == 0 && selectAll.value == false) {
    // select all
    selectAll.value = true;
    selectOption.value = [];
    for (let option of optionList.value) {
      selectOption.value.push(option.value);
    }
  } else if (selectAll.value == true && selectOption._rawValue.includes(0) == false) {
    // 전체 선택 토글
    selectAll.value = false;
    selectOption.value = [];
  } else {
    selectOption.value = val;
  }
  */
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
function connectSocketChat() {
  socketStore.connect("ws://70.12.107.143:80/enjoytrip/chat");
  socket.value.onopen = () => {
    console.log("Connected to server");
  };
}
function check() {
  vaild(
    (res) => {
      console.log(res);
      if ("유저 채팅방 입장 성공" == res.data.message) {
        isConnected.value = false;
        openChatModal();
        connectSocketChat();
      } else {
        isConnected.value = true;
        openModal();
      }
    },
    (err) => {
      console.log(err);
    }
  );
}
function test() {
  socketStore.connect("ws://70.12.107.143:80/enjoytrip/chat");
  socketStore.socket.send();
}
</script>

<template>
  <button @click="test">assssssssssssssssssssssssssss</button>
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
          <div class="modal-content"><ChatModal /></div>
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
          <th scope="col">충전소명</th>
          <th scope="col">충전소ID</th>
          <th scope="col">충전기상태</th>
          <th scope="col">위치</th>
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
