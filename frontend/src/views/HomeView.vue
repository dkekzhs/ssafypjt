<script setup>
import { ref, onMounted } from "vue";
import KakaoMap from "../components/layout/KakaoMap.vue";
import VSelect from "@/components/common/VSelect.vue";
import { getSido, getType, getGugun, getTravelSite } from "@/util/travel/travelApi";

const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const chargingStations = ref([]);
const selectStation = ref({});

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
    param.value,
    ({ data }) => {
      chargingStations.value = data.items[0].item;
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
};

const onChangeGugun = (val) => {
  param.value.zscode = val;
  getChargingStations();
};
</script>

<template>
  <h1>홈 화면입니다.</h1>
  <div class="container text-center mt-3">
    <div class="alert alert-success" role="alert">여행지 정보</div>
    <div class="row mb-2">
      <div class="col d-flex flex-row-reverse">
        <VSelect :selectOption="sidoList" @onKeySelect="onChangeSido" />
      </div>
      <div class="col"><VSelect :selectOption="gugunList" @onKeySelect="onChangeGugun" /></div>
    </div>

    <KakaoMap :stations="sidoList" :selectStation="selectStation" />
    <table class="table table-hover">
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
          :key="station.statId + station.chgerId"
          @click="viewStation(station)"
        >
          <th>{{ station.statNm }}</th>
          <td>{{ station.statId }}</td>
          <td>{{ station.stat }}</td>
          <td>{{ station.addr }}</td>
          <td>{{ station.lat }}</td>
          <td>{{ station.lng }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped></style>
