<script setup>
import { ref, onMounted } from "vue";
import KakaoMap from "../components/layout/KakaoMap.vue";
import VSelect from "@/components/common/VSelect.vue";
import {
  getSido,
  getType,
  getGugun,
  getTravelSite,
} from "@/util/travel/travelApi";
import VCheckbox from "../components/common/VCheckbox.vue";
import { useMenuStore } from "@/stores/menu";
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

const param = ref({
  serviceKey: VITE_OPEN_API_SERVICE_KEY,
  pageNo: 1,
  numOfRows: 20,
  zscode: 0,
});

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

function handleButtonClick(){
  alert("모달 오픈 해야됨");
}
</script>

<template>
  <h1>홈 화면입니다.</h1>

  <v-row class="v-row">
    
    <v-col class="v-col-4" >
      <VSelect class="v-text-field" :selectOption="sidoList" @onKeySelect="onChangeSido" />
      <VSelect :selectOption="gugunList" @onKeySelect="onChangeGugun" />
      <VCheckbox :selectOption="optionList" @onKeySelect="onChangeOption" />
      <!-- 모달 버튼-->
      <template v-if="menuStore.login" class="asdf">
        <v-col cols="auto">
        <v-btn class="asdfbtn" icon="mdi-plus" size="small" @click="handleButtonClick"></v-btn>
      </v-col>
    </template>

    </v-col>

    <v-col class="v-col-8">
      <KakaoMap :stations="chargingStations" :selectStation="selectStation" />
    </v-col>
  </v-row>

  <!-- 아래 출력-->
  <div class="container text-center mt-3">
    <v-data-table :headers="headers" :items="chargingStations" item-key="zipcode" @click="viewStation">
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
.v-row { 
  height: 100%;
}

.asdf {
  position: relative;
  float:left;
}

.asdfbtn {
  bottom: 0px;
}
</style>
