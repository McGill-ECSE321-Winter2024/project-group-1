<template>
  <div class="popup" id="mainContainer" style="margin-top: 150px;">
    <div class="popup-content">
      <VBox id="verticalContainer">
        <h2>{{ activity.name }}</h2>

        <p>
          Category: {{ activity.category }}<br>
          Date: {{ activity.date }}<br>
          Capacity: {{ activity.capacity }}
        </p>

        <button id="subButton" @click="cancel">Cancel</button>
        <button id="subButton" @click="close">Close</button>
      </VBox>
    </div>
  </div>
</template>

<script>
export default {
  props: ['activity'],
  methods: {
    close() {
      this.$emit('close');
    },
    cancel() {
      this.$emit('cancel');
    }
  }
};
</script>

<style src="../../assets/main.css"></style>