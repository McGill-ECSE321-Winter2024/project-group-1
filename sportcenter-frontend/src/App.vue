<template>
  <div id="app">
    
    <HBox id="horizontalContainer">
      <VBox id="verticalContainer" style="margin-right: 25px;">
        <h1>FullForm</h1>
        <h2>Fit N Fast</h2>
      </VBox>
      <img src="./assets/gym-icon.jpg" alt="Logo" contain height="110px" width="110px" style="border-radius: 55px;" @click="goHome()"></img>
    </HBox>


    <br>

    <HBox>
      <!-- <button id="menuButton" @click="goHome()">Home</button> -->
      <button id="menuButton" @click="goAccount()">Account</button>
      <button id="menuButton" @click="goActivity()">Activity</button>
      <button id="menuButton" @click="goInstructors()">Instructors</button>
      <button id="destroyButton" @click="goAuth()">Logout</button>
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
    }
  }
</script>

<style scoped src="./assets/main.css">
