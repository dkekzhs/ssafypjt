import { defineStore } from "pinia";

export const useSocketStore = defineStore("socketStore", {
  state: () => ({
    socket: null,
    isConnected: false,
    callback: null,
    messages: [],
    destinations: [],
    packet: null,
  }),
  getters: {
    getDestinations: (state) => state.destinations,
  }
  ,

  actions: {
    // 소켓 연결
    connect(url, callback) {
      return new Promise((resolve, reject) => {
        this.socket = new WebSocket("ws://localhost:80/enjoytrip" + url);
        this.callback = callback;
        this.socket.onopen = () => {
          console.log("WebSocket connected");
          this.isConnected = true;
          resolve();  // 연결이 완료되면 resolve를 호출합니다.
        };

        this.socket.onclose = () => {
          console.log("WebSocket disconnected");
          this.isConnected = false;
        };

        this.socket.onerror = (error) => {
          console.error("WebSocket error:", error);
          this.isConnected = false;
          reject(error);  // 에러 발생 시 reject를 호출합니다.
        };

        this.socket.onmessage = (event) => {
          this.packet = JSON.parse(event.data);
          console.log("Received message:", this.packet);
          console.log("Received message:", event);

          // 메시지 처리 또는 다른 작업 수행
          this.callback(this.packet);
        };
      })
    },

    // 소켓 연결 종료
    disconnect() {
      if (this.socket) {
        this.socket.close();
      }
    },

    // 메시지 전송
    async sendMessage(message) {
      console.log("잘돼요 비동기짱");
      if (this.isConnected) {
        this.socket.send(JSON.stringify(message));
      } else {
        console.warn("WebSocket is not connected");
      }
    },


    handlePacket() {
      console.log("핸들링할 패킷 >> " + this.packet);
      switch (this.packet.type) {
        case "getPlanList": {
          console.log("이전까지의 여행지를 가져옵니다.");
          console.log(this.packet.data);
          for (let i = 0; i < this.packet.data.length; i++) {
            this.destinations.push(this.packet.data[i]);
          }
        }
          break;
        case "message": {
          console.log("채팅방 메세지를 얻습니다.");
          this.messages.push({
            id: this.messages.length + 1
            , sender: this.packet.sender, content: this.packet.data
          });
        }
          break;
        case "addPlan": {
          console.log("여행지를 추가합니다.");
          this.destinations.push(this.packet.data);
          break;
        }

        case "deletePlan": {
          console.log("여행지를 삭제합니다.");
          this.destinations.filter((element) => {
            return element.content_id !== this.packet.data.content_id;
          })
          break;
        }

      }
    }
  },
});
