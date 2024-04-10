<template>
  <div class="ScheduleAcivity" id="mainContainer">
    <h1>Schedule an activity</h1>

    <Vbox id="verticalContainer">
      <HBox id="horizontalContainer">
        <input id="datePickerInput" type="date" name="scheduled" />
        <h2 style="align-self: center">from</h2>
        <input id="datePickerInput" type="time" name="startTime" />
        <h2 style="align-self: center">to</h2>
        <input id="datePickerInput" type="time" name="endTime" />
      </HBox>
      <select v-model="selectAccount">
        <option
          v-for="account in accounts"
          :key="account.accountRoleId"
          :value="account.accountRoleId"
        >
          {{ account.accountRoleId }}
        </option>
        >
      </select>
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
      <!--
            <input id="inputBox" type="text" placeholder="Account Role Id" v-model="accountRoleId">
            <input id="inputBox" type="text" placeholder="Activity Name" v-model="activityName2">
            -->
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
      //accountRoleId: null,
      //activityName2: null,
      capacity: null,
    };
  },

  methods: {
    async submitScheduleActivity() {
      const newScheduledActivity = {
        date: this.date,
        startTime: this.startTime,
        endTime: this.endTime,
        instructorId: this.instructorId,
        activityName: this.activityName,
        capacity: this.capacity,
      };
      try {
        date = date.toLocalDateString();
        startTime = startTime.toLocalTimeString();
        endTime = endTime.toLocalTimeString();
        /*
                const localDate = DateTime.fromJSDate(date).toLocal(); // Convert to local date
                date = localDate.toISODate(); 

                const localStartTime = DateTime.fromJSTime(startTime).toLocal(); // Convert to local time zone
                startTime = localStartTime.toISOTime();

                const localEndTime = DateTime.fromJSTime(endTime).toLocal(); // Convert to local time zone
                endTime = localEndTime.toISOTime(); 
                */

        const response = await AXIOS.post(
          "/createScheduledActivity/" +
            date +
            "/" +
            startTime +
            "/" +
            endTime +
            "/" +
            instructorId +
            "/" +
            activityName +
            "/" +
            capacity
        );
        console.log(response.data);
        this.clearInputs();
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
