<template>
  <div class="popup" id="mainContainer" style="margin-top: 150px">
    <div class="popup-content">
      <VBox id="verticalContainer">
        <h2>{{ activity.name }}</h2> 

        <p>
          Category: {{ activity.category }}<br />
          Date: {{ activity.date }}<br />
          Capacity: {{ activity.capacity }}
        </p>

        <button
          id="subButton"
          v-if="(getAccountType() === 'Customer')"
          @click="close"
        >
          Register
        </button>
        <button id="subButton" @click="close">Close</button>
      </VBox>
    </div>
  </div>
</template>

<script>
export default {
  props: ["activity"],
  methods: {
    close() {
      this.$emit("close");
    },
        getAccountType() {
      return localStorage.getItem('accountType');
    },
    setAccountType(accountType) {
      localStorage.setItem('accountType', accountType);
    },
    getUsername() {
      return localStorage.getItem('username');
    },
    setUsername(username) {
      localStorage.setItem('username', username);
    },
    getLoggedIn() {
      return localStorage.getItem('loggedIn') === 'true';
    },
    setLoggedIn(loggedIn) {
      localStorage.setItem('loggedIn', loggedIn);
    },
    getTime() {
      return localStorage.getItem('time');
    },
    setTime(time) {
      localStorage.setItem('time', time);
    },
    getDebuggingMode() {
      return localStorage.getItem('debugging_mode') === 'true';
    },
    setDebuggingMode(debugging_mode) {
      localStorage.setItem('debugging_mode', debugging_mode);
    },
    getLanguage() {
      return localStorage.getItem('language');
    },
    setLanguage(language) {
      localStorage.setItem('language', language);
    },
    getDarkMode() {
      return localStorage.getItem('dark_mode') === 'true';
    },
    setDarkMode(dark_mode) {
      localStorage.setItem('dark_mode', dark_mode);
    },
  },
};
</script>

<style src="../../assets/main.css"></style>
