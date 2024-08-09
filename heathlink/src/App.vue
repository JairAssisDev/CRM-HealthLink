<template>
  <div id="app">
    <AppHeader />
    <AppBanner />
  </div>
</template>

<script>
import AppHeader from './components/AppHeader.vue'
import AppBanner from './components/AppBanner.vue';

export default {
  name: 'App',
  components: {
    AppHeader,
    AppBanner
  }
}
</script>

<style>
</style>
