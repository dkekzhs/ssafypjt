import { defineStore } from "pinia";

export const useSocketStore = defineStore("socketStore", {
  state: () => ({
    socket: null,
    isConnected: false,
  }),

  actions: {
    // 소켓 연결
    connect(url) {
      this.socket = new WebSocket(url);

      this.socket.onopen = () => {
        console.log("WebSocket connected");
        this.isConnected = true;
      };

      this.socket.onclose = () => {
        console.log("WebSocket disconnected");
        this.isConnected = false;
      };

      this.socket.onerror = (error) => {
        console.error("WebSocket error:", error);
        this.isConnected = false;
      };

      this.socket.onmessage = (event) => {
        const message = JSON.parse(event.data);
        console.log("Received message:", message);
        // 메시지 처리 또는 다른 작업 수행
      };
      this.socket.send = () => {
        JSON.stringify({ type: "hello", message: "hello" });
      };
    },

    // 소켓 연결 종료
    disconnect() {
      if (this.socket) {
        this.socket.close();
      }
    },

    // 메시지 전송
    sendMessage(message) {
      console.log("진짜 그만");
      if (this.isConnected) {
        this.socket.send({ body: JSON.stringify("asdf") });
        this.socket.send(JSON.stringify(message));
      } else {
        console.warn("WebSocket is not connected");
      }
    },
  },
});
