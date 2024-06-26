<template>
  <div class="MyActivitiesTableCustomer" id="mainContainer">
    <h1>Activities I'm registered for</h1>
    <br />

    <table id="activityTable" align="center">
      <thead>
        <tr>
          <th width="20%">Name</th>
          <th width="20%">Category</th>
          <th width="20%">Date</th>
          <th width="20%">Capacity</th>
        </tr>
      </thead>
      <tbody id="myActivities">
        <template v-if="registrations.length === 0">
          <tr>
            <td colspan="5">No activities</td>
          </tr>
        </template>
        <template v-else>
          <tr
            v-for="(registration, index) in registrations"
            :key="index"
            @click="showActivityDetails(activity)"
          >
            <td>{{ registration.scheduledActivity.activity.name }}</td>
            <td>
              {{ registration.scheduledActivity.activity.subCategory }}
            </td>
            <td>{{ registration.scheduledActivity.date }}</td>
            <td>{{ registration.scheduledActivity.capacity }}</td>
            <td>
              <VBox id="verticalContainer">
                <button
                  id="subButton"
                  @click="cancelRegistration(registration)"
                >
                  Cancel Registration
                </button>
              </VBox>
            </td>
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

    async cancelRegistration(registration) {
      try {
        const response = await AXIOS.delete("/registration/" + registration.registrationId);

        if (response.status === 200) {
          alert("Registration cancelled successfully!");
          $forceUpdate();
        }
      } catch (error) {
        return;
      }
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
