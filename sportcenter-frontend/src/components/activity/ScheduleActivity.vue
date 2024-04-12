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
      <input id="inputBox" type="text" placeholder="Account Role Id" v-model="instructorId">
      <input id="inputBox" type="text" placeholder="Capacity" v-model="capacity">

      <table id="availableActivityTable" align="center" width="700">
        <thead>
          <tr>
            <th width="100">Name</th>
            <th width="100">Description</th>
            <th width="100">Subcategory</th>
            <th width="100">Status</th>
            <th width="100">Action</th>
          </tr>
        </thead>
        <tbody>
          <template v-if="activities.length === 0">
            <tr>
              <td colspan="5">No activities available for schedueling</td>
            </tr>
          </template>
          <template v-else>
            <tr v-for="(activity, index) in activities" :key="index">
              <td>{{ activity.name }}</td>
              <td>{{ activity.description }}</td>
              <td>{{ activity.subcategory }}</td>
              <td>{{ activity.status }}</td>
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

      activities: [],
      selectedActivity: null,
      instructorId: null,
      date: null,
      startTime: null,
      endTime: null,
      capacity: null,
    };
  },
  
  async created(){
    try {
    const response = await AXIOS.get("/activities");
    this.activities = response.data;
    } catch (error) {
    console.error("Error getting activities", error);
    }
  },
  methods:{
    selectedActivity(activity){
      this.selectedActivity = activity;
    },

    async submitScheduleActivity() {
      try {
        date = date.toLocalDateString();
        startTime = startTime.toLocalTimeString();
        endTime = endTime.toLocalTimeString();

        const response = await AXIOS.post(
          "/createScheduledActivity/" +
            this.date +
            "/" +
            this.startTime +
            "/" +
            this.endTime +
            "/" +
            this.instructorId +
            "/" +
            this.selectedActivity +
            "/" +
            this.capacity
        );
        console.log(response.data);
        this.clearInputs();
      } catch (error) {
        alert("Error creating scheduled activity");
        this.clearInputs();
      }
    },
    
    clearInputs() {
      this.instructorId = null;
      this.date = null;
      this.startTime = null;
      this.endTime = null;
      this.capacity = null;
      this.selectedActivity = null;
    },
  },
};
</script>

<style scoped src="../../assets/main.css"></style>
