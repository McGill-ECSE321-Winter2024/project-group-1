<template>
  <div class="popup" id="mainContainer" style="margin-top: 150px;">
    <div class="popup-content">
      <VBox id="verticalContainer">
        <h2>{{ activity.activity.name }}</h2> 

        <p>
          Category: {{ activity.activity.subCategory }}<br />
          Date: {{ activity.date }}<br />
          Capacity: {{ activity.capacity }}
        </p>

        <button
          id="subButton"
          v-if="(getAccountType() === 'Customer')"
          @click="registerActivity(activity)" 
        >
        <!--@click="close"-->
          Register
        </button>
        <button id="subButton" @click="close">Close</button>
      </VBox>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import config from '../../../config';

const frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
const backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;
const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  props: ["activity"],
  methods: {
    getAccountId() {
      return localStorage.getItem('accountId'); //Needs to be changed
    },
    async registerActivity(activity) {
      try {
        const customerID = await AXIOS.get('/getCustomerAccountRoleIdByUsername/' + this.getUsername());
        const scheduledActivityID = activity.scheduledActivityId;
        await AXIOS.post('/register/' + customerID.data + '/' + scheduledActivityID);
      } catch (error) {
        console.error(error);
      }
    },
    close() {
      this.$emit("close");
    },
    getAccountId() {
      return localStorage.getItem('accountId');
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
