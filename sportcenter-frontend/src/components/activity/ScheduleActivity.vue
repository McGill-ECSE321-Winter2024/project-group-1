<template>
  <div class="ScheduleAcivity" id="mainContainer">
    <h1>Schedule an activity</h1>

    <Vbox id="verticalContainer">
      <HBox id="horizontalContainer">
        <input id="datePickerInput" type="date" v-model="date" />
        <h2 style="align-self: center">from</h2>
        <input id="datePickerInput" type="time" v-model="startTime" />
        <h2 style="align-self: center">to</h2>
        <input id="datePickerInput" type="time" v-model="endTime" />
      </HBox>
      <!--input id="inputBox" type="text" placeholder="Account Role Id" v-model="instructorId"-->
      <input id="inputBox" type="text" placeholder="Capacity" v-model="capacity">

      <table id="availableActivityTable" align="center" width="700">
        <thead>
          <tr>
            <th width="100">Name</th>
            <th width="100">Description</th>
            <th width="100">Subcategory</th>
            <!--th width="100">Status</th-->
            <th width="100">Action</th>
          </tr>
        </thead>
        <tbody>
          <template v-if="activities.length === 0">
            <tr>
              <td colspan="4">No activities available for schedueling</td>
            </tr>
          </template>
          <template v-else>
            <tr v-for="(activity, index) in activities" :key="index">
              <td>{{ activity.name }}</td>
              <td>{{ activity.description }}</td>
              <td>{{ activity.subcategory }}</td>
              <!--td>{{ activity.isApproved ? "Approved" : "Not Approved" }}</td-->
              <td>
                <button @click="selectActivity(activity)">Select</button>
              </td>
            </tr>
          </template>
        </tbody>
      </table>
      <div v-if="selectedActivity">
        <h2>Selected Activity: {{ selectedActivity.name }}</h2>
      </div>

      <button id="mainButton" @click="submitScheduleActivity()">
        <b>Schedule Activity</b>
      </button>
    </Vbox>
  </div>
</template>

<script>
import axios, { Axios } from "axios";
import config from "../../../config";

const frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
const backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "ScheduleActivity",
  data() {
    return {

      activities: [],
      selectedActivity: null,
      date: null,
      startTime: null,
      endTime: null,
      capacity: null,
    };
  },
  
  async created(){
    try {
      const response = await AXIOS.get("/activitiesByIsApproved/true");
      this.activities = response.data;
    } catch (error) {
      console.error("Error getting activities", error);
    }
  },
  methods:{
    async selectActivity(activity){
      this.selectedActivity = activity;
    },

    async submitScheduleActivity() {
      let instructorId = '';
      let dateInput = this.date;
      let startTimeInput = this.startTime;
      let endTimeInput = this.endTime;
      try {
        instructorId = await AXIOS.get("/getInstructorAccountRoleIdByUsername/" + this.getUsername());
        console.log(instructorId.data);
      } catch (error) {
        alert("Error getting instructor id");
        return;
      }
      try {
        // dateInput = dateInput.toLocalDateString();
        // startTimeInput = startTimeInput.toLocalTimeString();
        // endTimeInput = endTimeInput.toLocalTimeString();

        const response = await AXIOS.post(
          "/createScheduledActivity/" +
            dateInput +
            "/" +
            startTimeInput +
            "/" +
            endTimeInput +
            "/" +
            instructorId.data +
            "/" +
            this.selectedActivity.name +
            "/" +
            this.capacity
        );
        console.log(response.data);
        this.clearInputs();
      } catch (error) {
        alert("Error creating scheduled activity " + error).getMessage();
      }
    },
    clearInputs() {
      this.date = null;
      this.startTime = null;
      this.endTime = null;
      this.capacity = null;
      this.selectedActivity = null;
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

<style scoped src="../../assets/main.css"></style>
