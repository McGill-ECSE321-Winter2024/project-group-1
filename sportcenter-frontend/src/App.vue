<template>
  <div id="app">
    <img src="./assets/logo_internet.png" width=25%>
    <br>
    <h1><b>SportCenter</b></h1>
    <h2><i>Get fit, not fat!</i></h2>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 15%;
    align-content: center;
    display: flex;
    flex-direction: column;
    justify-items: center;
    align-items: center;
  }
</style>
