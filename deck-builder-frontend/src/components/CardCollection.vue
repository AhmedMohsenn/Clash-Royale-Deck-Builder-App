<template>
  <div class="card-collection">
    <h4 class="card-collection-header">Card Collection</h4>
    <div class="card-collection-wrapper">
      <div v-for="card of data"
           v-bind:key="card.id"
           class="card">
        <card :card="card"
              :handle-click-action="clickOnCollectionCard" />
      </div>
    </div>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
export default {
  name: "card-collection",
  components: { Card },
  props: {
    data: Array
  },
  methods: {
    clickOnCollectionCard: function() {
      let clickTypes = ["moreInfo", "upgrade"];
      return clickTypes;
    }
  }
};
</script>

<style scoped>
.card-collection-wrapper {
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
}

.card {
  flex-basis: 22%;
  margin-top: 2%;
  border: none;
}
</style>
