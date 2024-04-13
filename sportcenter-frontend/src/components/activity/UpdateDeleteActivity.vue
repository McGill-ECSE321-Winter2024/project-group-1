<template>
  <div style="width: 1200px;">
    <div class="UpdateDeleteActivity" id="mainContainer" style="margin-bottom: 0px;">
      <h1>Activity Management</h1>
      <br>

      <table id="activityTable" align="center" width="700">
        <thead>
          <tr>
            <th width="100">Name</th>
            <th width="100">Category</th>
            <th width="100">Description</th>
            <th width="100">Approved</th>
          </tr>
        </thead>
        <tbody>
          <template v-if="activities.length === 0">
            <tr>
              <td colspan="4">No Activities</td>
            </tr>
          </template>

          <template v-else>
            <tr v-for="(activity, index) in activities" :key="index">
              <td>{{ activity.activity.name }}</td>
              <td>{{ activity.activity.subCategory }}</td>
              <td>{{ activity.description }}</td>
              <td>{{ activity.is_approved }}</td>
            </tr>
          </template>

        </tbody>
      </table>
      <ViewActivity v-if="selectedActivity" :activity="selectedActivity" @close="closePopup" style="align-self: center;"/>
      <br>
    </div>

    <div id="horizontalContainer">
      <div id="mainContainer" style="width: 560px;">
        <h1>Update activity</h1>
        <VBox>

          <input id="inputBox" placeholder="Name of activity to update" v-model="name">
          <input id="inputBox" placeholder="Updated category" v-model="category">
          <input id="inputBox" placeholder="Updated capacity" v-model="capacity">
          <button id="mainButton" @click="updateActivity()"><b>Update</b></button>
        </VBox>
      </div>

      <div id="mainContainer" style="width: 560px; margin-left: 80px;">
        <h1>Delete activity</h1>
        <input id="inputBox" placeholder="Name of activity to delete" v-model="name2">
        <input id="inputBox" placeholder="Confirm name of activity to delete">
        <input id="inputBox" placeholder="Write 'DELETE' to confirm deletion">
        <button id="destroyButton" @click="deleteActivity()" style="width: 100%;"><b>Delete</b></button>
      </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios'
import config from '../../../config'
import ViewActivity from './ViewActivity.vue';

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  data() {
    return {
      activities: [],
      filteredActivityData: [],
      selectedActivity: null,
      search:'',
    };
  },

  async created() {
     
     try {
      const isApproved = true;
      const response = await AXIOS.get('/activitiesByIsApproved/' + isApproved);
       this.activities = response.data;
      }
      catch (error) {
        console.error('Error fetching scheduled activities:', error);
      }
    },

  methods: {    
    async updateActivity() {
      const updatedActivity = {
        name: this.name,
        category: this.category,
        capacity: this.capacity
      };
      try {
        const response = await AXIOS.put('/updateActivity/' + this.name + '/' + this.category + '/' + this.capacity);
        this.scheduledActivities.push(response.data);
        this.clearInputs();
      } catch (error) {
        console.error('Error updating activity:', error);
      }
    },
    async deleteActivity() {
      const deletedActivity = {
        name2: this.name2
      };
      try {
        const response = await AXIOS.delete('/deleteActivity/', + this.name2);
        this.scheduledActivities.push(response.data);
        this.clearInputs();
        console.log('Activity deleted:', response.data);
      } catch (error) {
        console.error('Error deleting activity:', error);
      }
    },

    clearInputs() {
      this.activityName = null;
      this.category = null;
      this.capacity = null;
    },
 
    showActivityDetails(activity) {
      this.selectedActivity = activity;
    },
    closePopup() {
      this.selectedActivity = null;
    },
  },

  computed: {
  filteredActivities: function() {
    const query = this.search.toLowerCase();
    return this.scheduledActivities.filter(activity =>
      activity.name.toLowerCase().includes(query) ||
      activity.category.toLowerCase().includes(query) ||
      activity.date.toLowerCase().includes(query) ||
      activity.capacity.toString().includes(query)
    );
  },
  },
  components: {
    ViewActivity
  }
};
</script>

<style src="../../assets/main.css"></style>