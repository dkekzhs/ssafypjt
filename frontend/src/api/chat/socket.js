import { defineStore } from "pinia";

export const useSocketStore = defineStore("socketStore", {
  state: () => ({
    socket: null,
    isConnected: false,
  }),

  actions: {
    // 소켓 연결
    connect(url) {
      return new Promise((resolve, reject) => {
        this.socket = new WebSocket("ws://localhost:80/enjoytrip" + url);

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
          const message = JSON.parse(event.data);
          console.log("Received message:", message);
          console.log("Received message:", event);

          // 메시지 처리 또는 다른 작업 수행
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
  },
});
