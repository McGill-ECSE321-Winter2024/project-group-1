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
      <!--select v-model="selectAccount">
        <option
          v-for="account in accounts"
          :key="account.accountRoleId"
          :value="account.accountRoleId"
        >
          {{ account.accountRoleId }}
        </option>
        >
      </select-->
      <br />
      <select v-model="selectActivity">
        <option
          v-for="activity in activities"
          :key="activity.name"
          :value="activity.name"
        >
          {{ activity.name }}
        </option>
        >
      </select>
            <!--input id="inputBox" type="text" placeholder="Account Role Id" v-model="accountRoleId"-->
            <!--input id="inputBox" type="text" placeholder="Activity Name" v-model="activityName2"-->
      <input
        id="inputBox"
        type="text"
        placeholder="Capacity"
        v-model="capacity"
      />

      <button id="mainButton" @click="submitScheduleActivity()">
        <b>Schedule Activity</b>
      </button>
    </Vbox>
  </div>
</template>

<script>
import axios from "axios";
import config from "../../../config";

const frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
const backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  //name: 'Fabian',
  name: "ScheduleActivity",
  data() {
    return {
      accounts: [],
      selectAccount: null,
      activities: [],
      selectActivity: null,

      activityName: null,
      description: null,
      subcategory: null,
      date: null,
      startTime: null,
      endTime: null,
      capacity: null,
    };
  },
  created() {
    this.getActivities();
  },

  methods: {
    async getActivities() {
      try {
        const response = await AXIOS.get("/activities");
        this.activities = response.data;
      } catch (error) {
        alert("Error getting activities");
      }
    },
    async submitScheduleActivity() {
      try {
        const response1 = await AXIOS.get(
          "/getInstructorByUsername/" + this.$username
        );
        const response = await AXIOS.post(
          "/createScheduledActivity/" +
            this.date +
            "/" +
            this.startTime +
            "/" +
            this.endTime +
            "/" +
            response1.accountRoleId +
            "/" +
            this.selectActivity.name +
            "/" +
            this.capacity
        );
        console.log(response.data);
      } catch (error) {
        alert("Error creating scheduled activity");
        this.clearInputs();
      }
    },
    clearInputs() {
      this.description = null;
      this.subcategory = null;
      this.selectAccount = null;
      this.selectActivity = null;
      this.instructorId = null;
      this.activityName = null;
      this.date = null;
      this.startTime = null;
      this.endTime = null;
      this.capacity = null;
    },
  },
};
</script>

<style scoped src="../../assets/main.css"></style>
