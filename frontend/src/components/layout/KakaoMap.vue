<script setup>
import { ref, onMounted, watch } from "vue";
import serviceKey from "./key.js";

const mapContainer = ref(null);
let map, clusterer, positions = [], filters = [], markers = [];

onMounted(() => {
  let mapOption = {
    center: new kakao.maps.LatLng(37.500613, 127.036431),
    level: 13,
  };
  
  map = new kakao.maps.Map(mapContainer.value, mapOption);
  clusterer = new kakao.maps.MarkerClusterer({
    map: map,
    averageCenter: true,
    minLevel: 10,
  });

  fetchInit();
});

const fetchInit = () => {
  // Your fetchInit function logic here
  map.setLevel(13);
  let baseUrl = `https://apis.data.go.kr/B551011/KorService1/`;
  let queryString = `serviceKey=${serviceKey}&numOfRows=200&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
  let service =   `areaBasedList1`;
  let searchUrl = baseUrl + service + "?" + queryString;
  fetch(searchUrl)
    .then((response) => response.json())
    .then((data) => makeList(data));
}

const fetchPlace = () => {
  // Your fetchPlace function logic here
  let baseUrl = `https://apis.data.go.kr/B551011/KorService1/`;
  let queryString = `serviceKey=${serviceKey}&numOfRows=100&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
  let areaCode = document.getElementById("sido").value;
  let sigunguCode = document.getElementById("gugun")?.value;
  if(areaCode){
    map.setLevel(11);
  }
  if (areaCode && parseInt(areaCode)) queryString += `&areaCode=${areaCode}`;
  if (sigunguCode ){
    queryString += `&sigunguCode=${sigunguCode}`;
  }
  let service = ``;
  if (keyword) {
    service = `searchKeyword1`;
    queryString += `&keyword=${keyword}`;
  } else {
    service = `areaBasedList1`;
  }
  let searchUrl = baseUrl + service + "?" + queryString;

  fetch(searchUrl)
    .then((response) => response.json())
    .then((data) => makeList(data));
}

const fetchPlaceByContentTypeId = () => {
  // Your fetchPlaceByContentTypeId function logic here
  if(filters.length==0){
    if(!document.getElementById("sido").value){
      fetchInit();
    }else{
      fetchPlace(sido);
    }
    return;
  }
  positions=[];
  let processed=0;
  if(filters.length==7){
    fetchPlace();
    return;
  }
  filters.forEach((filter,index)=>{
    let baseUrl = `https://apis.data.go.kr/B551011/KorService1/`;
    let queryString = `serviceKey=${serviceKey}&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
    let areaCode = document.getElementById("sido").value;
    let sigunguCode = document.getElementById("gugun")?.value;
    if(areaCode){
      map.setLevel(10);
    }
    let keyword = '';
    if (parseInt(areaCode)) queryString += `&areaCode=${areaCode}`;
    
    queryString += `&contentTypeId=${filter}`;
    
    let service = ``;
    if (sigunguCode ){
      queryString += `&sigunguCode=${sigunguCode}`;
    }
    if (keyword) {
      service = `searchKeyword1`;
      queryString += `&keyword=${keyword}`;
    } else {
      service = `areaBasedList1`;
    }
    let searchUrl = baseUrl + service + "?" + queryString;
    fetch(searchUrl)
    .then((response) => response.json())
    .then((data) => {
     
      let trips = data.response.body.items.item;
      trips?.forEach(({ title, firstimage, firstimage2, addr1, addr2, mapy, mapx, tel, zipcode }) => {
        let markerInfo = {
          title,
          firstimage,
          firstimage2,
          latlng: new kakao.maps.LatLng(mapy, mapx),
          addr1,
          addr2,
          tel,
          zipcode
        };
        positions.push(markerInfo);
      });
      processed++;
      if(processed==(filters.length)){
        displayMarker();
      }
    });
  });
  
}

const makeList = (data) => {
  // Your makeList function logic here
  let trips = data.response.body.items.item;
  
  positions = [];
  trips?.forEach(({ title, firstimage, firstimage2, addr1, addr2, mapy, mapx, tel, zipcode }) => {
    let markerInfo = {
      title,
      firstimage,
      firstimage2,
      latlng: new kakao.maps.LatLng(mapy, mapx),
      addr1,
      addr2,
      tel,
      zipcode
    };
    positions.push(markerInfo);
  });
  
  //document.getElementById("trip-list").innerHTML = tripList;
  displayMarker();
}

const displayMarker = () => {
  // Your displayMarker function logic here
  if(markers.length>0){
    clusterer.removeMarkers(markers);
    markers=[];
  }

  // 마커 이미지의 이미지 주소입니다
  var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
  for (var i = 0; i < positions.length; i++) {
    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(24, 35);

    // 마커 이미지를 생성합니다
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: positions[i].latlng, // 마커를 표시할 위치
      title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      image: markerImage, // 마커 이미지
      clickable: true
    });
    markers.push(marker);
     // 마커 위에 커스텀오버레이를 표시합니다
    // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
   // 마커에 표시할 인포윈도우를 생성합니다 
   var content = `
    <div class="wrap">
      <div class="info">  
        <div class="title">
            ${positions[i].title}
          </div>
        <div class="body">
              <div class="img">
                  <img src=${positions[i].firstimage ?? positions[i].firstimage2 ?? "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png"} width="73" height="70">
            </div>
              <div class="desc">
                <div class="ellipsis">${positions[i].addr1}</div>
                <div class="jibun ellipsis">${positions[i].addr2}</div>
                <div class="jibun ellipsis">(우) ${positions[i].zipcode} (tel) ${positions[i].tel}</div>
            </div>
        </div>
      </div>   
    </div>
    `;
 
   var infowindow = new kakao.maps.InfoWindow({
    content // 인포윈도우에 표시할 내용
   });
    
   // 마커에 이벤트 추가
   // mouseover: 정보창 표시
   // mouseout: 정보창 비표시
   kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
   kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));

  }
  clusterer.addMarkers(markers);
  // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
  if(map.getLevel()!=13){
  map.setCenter(positions[0].latlng);}
}

const makeOverListener = (map, marker, infowindow) => {
  return function() {
      infowindow.open(map, marker);
  };
}

const makeOutListener = (infowindow) => {
  return function() {
      infowindow.close();
  };
}

watch(filters, () => {
  fetchPlaceByContentTypeId();
})
</script>

<template>
<div>
  <select id="sido" @change="fetchPlace">
    <!-- Your options here -->
  </select>
  <select id="gugun" @change="fetchPlace">
    <!-- Your options here -->
  </select>
  <div id="map" ref="mapContainer"></div>
  <button v-for="filter in filters" @click="toggleFilter(filter.value)">{{ filter.name }}</button>
</div>
</template>

<style scoped>
#map {
  width: 100%;
  height: 400px;
}
</style>