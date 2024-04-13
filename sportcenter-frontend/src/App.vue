<template>
  <div id="app">
    <HBox id="horizontalContainer">
      <VBox id="verticalContainer" style="margin-right: 25px;">
        <h1>FullForm</h1>
        <h2>Put yourself first<br>{{ getTime() }}</h2>
      </VBox id="verticalContainer">
      <img src="./assets/gym-icon.jpg" alt="Logo" contain height="140px" width="140px" style="border-radius: 50%;" @click="goHome()"></img>
    </HBox>
    <br>
    <HBox v-if="this.getLoggedIn() == 'true'">
      <button id="menuButton" @click="goHome()">Home</button>
      <button id="menuButton" v-if="this.getAccountType() === 'Customer'" @click="goCustomerAccount()">Account</button>
      <button id="menuButton" v-if="getAccountType() === 'Instructor'" @click="goInstructorAccount()">Account</button>
      <button id="menuButton" v-if="getAccountType() === 'Owner'" @click="goOwnerAccount()">Account</button>
      <button id="menuButton" v-if="getAccountType() === 'Customer'" @click="goCustomerMyActivities()">My Activities</button>
      <button id="menuButton" v-if="getAccountType() === 'Instructor'" @click="goInstructorMyActivities()">My Activities</button>
      <button id="menuButton" @click="goActivity()">All activities</button>
      <button id="menuButton" v-if="getAccountType() != 'Owner'" @click="goInstructors()">Instructors</button>
      <button id="menuButton" v-if="getAccountType() =='Owner'" @click="goInstructorsForOwner()">Instructors</button>
      <button id="destroyButton" @click="goLogout()">Logout</button> <!--TO CHANGE-->
    </HBox>
    <HBox v-else>
      <button id="menuButton" @click="goLogin()">Login</button>
      <button id="menuButton" @click="goCreateAccount()">Create account</button>
    </HBox>
    <router-view style="margin-bottom: 80px;"></router-view>
    <p style="margin-bottom: 80px;">© 2024 FullForm (Group 1)</p>
  </div>
</template>

<script>
import Vue from 'vue';

export default {
    name: 'app',
    data() {
      return {
        accountType: localStorage.getItem('accountType') || 'Guest',
        username: localStorage.getItem('username') || 'JoeMama',
        loggedIn: localStorage.getItem('loggedIn') === 'true',
        time: localStorage.getItem('time') || (new Date().getDate() + "/" + (new Date().getMonth() + 1) + "/" + new Date().getFullYear()),
        debugging_mode: localStorage.getItem('debugging_mode') === 'true',
        language: localStorage.getItem('language') || 'en',
        dark_mode: localStorage.getItem('dark_mode') === 'false'
      };
    },
  methods: {
    // Methods for global variables
    setAccountId(id) {
      localStorage.setItem('id', id);
    },
    getAccountId() {
      return localStorage.getItem('id');
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
      return localStorage.getItem('loggedIn');
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
    // Methods for navigation
    goHome() {
      this.$router.push('/');
    },
    goCustomerAccount() {
      this.$router.push('/app/account/customer-account');
    },
    goInstructorAccount() {
      this.$router.push('/app/account/instructor-account');
    },
    goOwnerAccount() {
      this.$router.push('/app/account/owner-account');
    },
    goCustomerMyActivities() {
      this.$router.push('/app/my-activities/customer');
    },
    goInstructorMyActivities() {
      this.$router.push('/app/my-activities/instructor');
    },
    goActivity() {
      this.$router.push('/app/activity');
    },
    goLogin() {
      this.$router.push('/app/auth/login');
    },
    goLogout() {
      
      
      this.setLoggedIn(false);
      this.setAccountType('Guest');
      this.setUsername('');
      this.setAccountId('');
      this.$router.push('/app/auth/login');

    },
    goCreateAccount() {
      this.$router.push('/app/auth/createaccount');
    },
    goInstructors() {
      this.$router.push('/app/instructors/view-instructors');
    },
    goInstructorsForOwner() {
      this.$router.push('/app/instructors/manage-instructors');
    },
    goHome() {
      this.$router.push('/');
    }
  
  },


  watch: {
  // Watch for changes in the accountType variable
  accountType: {
    handler(newVal) {
      // Update UI or perform actions based on the new value
      console.log('Account Type changed:', newVal);
    },
    immediate: true // Trigger the watcher immediately upon component creation
  },

  // Similarly, define watchers for other global variables like username, loggedIn, etc.
  username: {
    handler(newVal) {
      console.log('Username changed:', newVal);
    },
    immediate: true
  },

  loggedIn: {
    handler(newVal) {
      console.log('LoggedIn changed:', newVal);
    },
    immediate: true
  },

  // Define watchers for other global variables here...
}




}
</script>

<style scoped src="./assets/main.css">
