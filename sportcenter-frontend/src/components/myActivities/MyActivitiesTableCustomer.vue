<template>
  <div class="MyActivitiesTableCustomer" id="mainContainer">
    <h1>Activities I'm registered for</h1>
    <br>
    
    <table id="activityTable" align="center" width="700">
      <thead>
        <tr>
          <th width="100">Name</th>
          <th width="100">Category</th>
          <th width="100">Date</th>
          <th width="100">Capacity</th>
        </tr>
      </thead>
      <tbody>
        <template v-if="scheduledActivities.length === 0">
          <tr>
            <th width="100">Name</th>
            <th width="100">Category</th>
            <th width="100">Date</th>
            <th width="100">Capacity</th>
          </tr>
        </thead>
        <tbody id="myActivities">
          <template v-if="registrations.length === 0">
            <tr>
              <td colspan="4">No activities</td>
            </tr>
          </template>
          <template v-else>
            <tr
              v-for="(registrations, index) in registrations"
              :key="index"
              @click="showActivityDetails(activity)"
            >
              <td>{{ registrations.scheduledActivity.activity.name }}</td>
              <td>
                {{ registrations.scheduledActivity.activity.subCategory }}
              </td>
              <td>{{ registrations.scheduledActivity.date }}</td>
              <td>{{ registrations.scheduledActivity.capacity }}</td>
            </tr>
          </template>
        </tbody>
      </table>
    </VBox>

    <ViewActivity
      v-if="selectedActivity"
      :activity="selectedActivity"
      @close="closePopup"
      style="align-self: center"
    />
  </div>
</template>

<script>
import axios from "axios";
import config from "../../../config";
import ViewActivity from "./MyActivitiesCustomer.vue";

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
      isCustomer: this.getAccountType() === "Customer",
      customerId: this.getAccountId(),
      registrations: [],
      filteredActivityData: [],
      selectedActivity: null,
      search: "",
    };
  },

  async created() {
    // Make HTTP request to fetch scheduled activities from backend
    try {
      const id = await AXIOS.get(
        "/getCustomerAccountRoleIdByUsername/" + this.getUsername()
      );
      const response = await AXIOS.get(
        "/getRegistrationsByAccountRoleId/" + id.data
      );
      this.registrations = response.data;
    } catch (error) {
      console.error("Error fetching scheduled activities:", error);
    }
  },

  // mounted() {
  //   // Call method to fetch scheduled activities when the component is mounted
  //   this.fetchScheduledActivities();
  // },

  methods: {
    fetchSchedulesActivities() {
      axios
        .get("/getRegistrationsByAccountRoleId/" + this.customerId)
        .then((response) => {
          this.registrations = response.data;

          this.scheduledActivitiesTable = response.data.map((registration) => ({
            activityName: registration.scheduledActivity.activity.name,
            activityCategory: registration.scheduledActivity.activity.category,
            date: registration.scheduledAtivity.date,
            capacity: registration.sheduledActivity.capacity,
          }));
        })
        .catch((error) => {
          console.error("Error fetching scheduled activities:", error);
        });
    },
    // fetchScheduledActivities() {
    //   // Make HTTP request to fetch scheduled activities from backend
    //   axios.get('/scheduledActivities')
    //     .then(response => {
    //       // Assign response data to scheduledActivities
    //       this.scheduledActivities = response.data;

    //       this.scheduledActivitiesTable = response.data.map(activity => ({
    //       activityName: activity.activity.name,
    //       activityCategory: activity.activity.category,
    //       date: activity.date,
    //       capacity: activity.capacity
    //     }));
    //     })
    //     .catch(error => {
    //       console.error('Error fetching scheduled activities:', error);
    //     });
    // },
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
    getUsername() {
      return localStorage.getItem("username");
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
