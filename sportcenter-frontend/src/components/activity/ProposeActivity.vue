<template>
  <div class="Fabian" id="mainContainer">
    <h1>Propose an activity</h1>

    <VBox id="verticalContainer">
      <input
        id="inputBox"
        type="text"
        placeholder="Activity name"
        v-model="activityName"
      />
      <input
        id="inputBox"
        type="text"
        placeholder="Description"
        v-model="description"
      />
      <input
        id="inputBox"
        type="text"
        placeholder="Subcategory - Cardio, Strength, Stretch"
        v-model="subcategory"
      />
      <!--<button id="mainButton" @click="created()">GetActivities</button>-->
      <button id="mainButton" @click="submitProposeActivity()">
        Propose to the owner
      </button>
    </VBox>
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
  name: "Activities",
  data() {
    return {
      activities: [],
      activityName: null,
      description: null,
      subcategory: null,
      date: null,
      startTime: null,
      endTime: null,
      accountRoleId: null,
      activityName2: null,
      capacity: null,
    };
  },
  methods: {
    async submitProposeActivity() {
      try {
        const response = await AXIOS.post(
          "/createActivity/" +
            this.activityName +
            "/" +
            this.description +
            "/" +
            this.subcategory
        );
        console.log(response.data);
        this.clearInputs();
      } catch (error) {
        alert("Problem proposing activity. Please try again. " + error.message);
      }
    },
    clearInputs() {
      this.activityName = null;
      this.description = null;
      this.subcategory = null;
      this.date = null;
      this.startTime = null;
      this.endTime = null;
      this.accountRoleId = null;
      this.activityName2 = null;
      this.capacity = null;
    },
  },
};
</script>

<style scoped src="../../assets/main.css"></style>
