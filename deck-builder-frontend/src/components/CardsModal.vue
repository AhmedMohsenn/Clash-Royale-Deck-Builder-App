<template>
  <b-modal id="cards-modal"
           size="lg"
           hide-footer
           title="Select Card"
           @show="currentCards = getCardsByRarity(cards, cardsRarities[0])"
           @hide="$emit('hide-collections-modal')">

    <b-tabs>
      <b-tab v-for="rarity of cardsRarities"
             v-bind:key="rarity"
             :title="rarity"
             @click="getCardsByRarity(cards, rarity)">
        <div class="cards-wrapper">
          <card v-for="card of currentCards"
                v-bind:key="card.id"
                :card="card"
                :handle-click-action="clickOnCard"
                @use-card="useCard($event)" />
        </div>
      </b-tab>
    </b-tabs>
  </b-modal>
</template>

<script>
import card from "@/components/Card.vue";
export default {
  name: "cards-modal",
  components: { card },
  props: {
    showCollections: Boolean,
    cards: Array,
    cardsRarities: Array
  },

  data() {
    return {
      currentCards: []
    };
  },

  methods: {
    getCardsByRarity: function(cards, rarity) {
      this.currentCards = cards.filter(c => c.rarity === rarity);
    },
    clickOnCard: function() {
      return ["use"];
    },
    useCard(card) {
      this.$emit("use-card", card);
    }
  },

  watch: {
    showCollections: function(value) {
      if (value) {
        this.$bvModal.show("cards-modal");
      } else {
        this.$bvModal.hide("cards-modal");
      }
    }
  }
};
</script>

<style scoped>
.cards-wrapper {
  display: flex;
  flex-flow: row wrap;
}

.cards-wrapper {
  padding: 0.5rem;
}

.cards-wrapper > .card {
  flex-basis: 10%;
  margin-right: 1rem;
  margin-top: 1rem;
}
</style>
