<template>
  <div id="app">
    
    <HBox id="horizontalContainer">
      <VBox id="verticalContainer" style="margin-right: 25px;">
        <h1>SportCenter</h1>
        <h2>Get fit n fast!</h2>
      </VBox>
      <img src="./assets/gym-icon.jpg" alt="Logo" contain height="110px" width="110px" style="border-radius: 55px;"></img>
    </HBox>


    <br>

    <HBox>
      <button id="menuButton" @click="goHome()">Home</button>
      <button id="menuButton" @click="goAccount()">Account</button>
      <button id="menuButton" @click="goActivity()">Activity</button>
      <button id="menuButton" @click="goInstructors()">Instructors</button>
      <button id="logoutButton" @click="goAuth()">Logout</button>
    </HBox>
    
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'app',
    methods: {
      goHome() {
        this.$router.push('/app/home');
      },
      goAccount() {
        this.$router.push('/app/account');
      },
      goActivity() {
        this.$router.push('/app/activity');
      },
      goAuth() {
        this.$router.push('/app/auth');
      },
      goInstructors() {
        this.$router.push('/app/instructors');
      }
    },
    created() {
      this.currentDate = new Date().getDay() + "/" + new Date().getMonth() + "/" + new Date().getFullYear();
    },
    data() {
      return {
      currentDate: null
      };
    },
  }
</script>

<style scoped src="./assets/main.css">
