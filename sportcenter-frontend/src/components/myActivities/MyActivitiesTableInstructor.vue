<template>
  <div class="MyActivitiesTable" id="mainContainer">
    <h1>View the activities I have to teach</h1>
    <br />

    <VBox id="verticalContainer">
      <table id="activityTable">
        <thead>
          <tr>
            <th width="100">Name</th>
            <th width="100">Category</th>
            <th width="100">Date</th>
            <th width="100">Capacity</th>
          </tr>
        </thead>
        <tbody id="myActivities">
          <template v-if="scheduledActivities.length === 0">
            <tr>
              <td colspan="4">No activities</td>
            </tr>
          </template>
          <template v-else>
            <tr
              v-for="(scheduledActivities, index) in scheduledActivities"
              :key="index"
              @click="showActivityDetails(activity)"
            >
              <td>{{ scheduledActivities.activity.name }}</td>
              <td>{{ scheduledActivities.activity.subCategory }}</td>
              <td>{{ scheduledActivities.date }}</td>
              <td>{{ scheduledActivities.capacity }}</td>
            </tr>
          </template>
        </tbody>
      </table>

      <ViewActivity
        v-if="selectedActivity"
        :activity="selectedActivity"
        @close="closePopup"
        style="align-self: center"
      />
    </VBox>
  </div>
</template>

<script>
import axios from "axios";
import config from "../../../config";
import ViewActivity from "./MyActivitiesInstructor.vue";

const frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
const backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;
const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  data() {
    return {
      isInstructor: this.getAccountType() === "Instructor",
      instructorId: this.getAccountId(),
      scheduledActivities: [],
      filteredActivityData: [],
      selectedActivity: null,
      search: "",

      //logic here would get all activities

      // scheduledActivities: [
      //   { name: 'Borneo', category: 'Expedition', date: '6 march', capacity: 30 },
      //   { name: 'Trifecta', category: 'YoloSwag', date: '6 april', capacity: 10 },
      //   { name: 'Running', category: 'Cardio', date: '6 january', capacity: 20 },
      //   { name: 'INSTAGATION', category: 'Vroom', date: '6 january', capacity: 20 },
      // ],
    };
  },

  async created() {
    // Make HTTP request to fetch scheduled activities from backend
    try {
      const id = await AXIOS.get("/getInstructorAccountRoleIdByUsername/" + this.getUsername());
      const response = await AXIOS.get("/scheduledActivities/instructor/" + id.data);
      this.scheduledActivities = response.data;
    } catch (error) {
      console.error("Error fetching scheduled activities:", error);
    }
  },

  methods: {
    fetchScheduledActivities() {
      // Make HTTP request to fetch scheduled activities from backend
      axios
        .get("/scheduledActivities/instructor/" + this.instructorId)
        .then((response) => {
          // Assign response data to scheduledActivities
          this.scheduledActivities = response.data;

          this.scheduledActivitiesTable = response.data.map((activity) => ({
            activityName: activity.activity.name,
            activityCategory: activity.activity.category,
            date: activity.date,
            capacity: activity.capacity,
          }));
        })
        .catch((error) => {
          console.error("Error fetching scheduled activities:", error);
        });
    },

    getAccountId() {
      return localStorage.getItem("id");
    },

    getAccountType() {
      return localStorage.getItem("accountType");
    },
    getUsername() {
      return localStorage.getItem("username");
    },
    showActivityDetails(activity) {
      this.selectedActivity = activity;
    },
    closePopup() {
      this.selectedActivity = null;
    },
  }, //end of methods

  computed: {
    // Filter activities based on search query
    filteredActivities: function () {
      const query = this.search.toLowerCase();
      return this.scheduledActivities.filter(
        (activity) =>
          activity.name.toLowerCase().includes(query) ||
          activity.category.toLowerCase().includes(query) ||
          activity.date.toLowerCase().includes(query) ||
          activity.capacity.toString().includes(query)
      );
    },
  },
  components: {
    ViewActivity,
  },
};
</script>

<style src="../../assets/main.css"></style>
