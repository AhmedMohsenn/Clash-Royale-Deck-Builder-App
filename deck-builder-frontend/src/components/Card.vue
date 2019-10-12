<template>
  <div class="card"
       v-click-outside="clearEvents"
       :class="{'empty-card' : isEmpty}">

    <div class="card-image"
         @click="getPossibleEvents()">
      <font-awesome-icon icon="plus"
                         v-if="isEmpty"
                         class="add-card-icon"
                         size="sm" />

      <img v-else
           :src="getCardImage(card)"
           alt="card image" />
    </div>

    <div v-if="!isEmpty"
         class="card__elixir">
      <p>{{ card.elixirCost }}</p>
    </div>

    <b-button-group vertical
                    class="action-btns">
      <b-button v-if="events.includes('moreInfo')"
                variant="primary"
                v-b-modal="'info-modal' + card.id">Info</b-button>
      <b-button @click="emitEvent('remove-mode')"
                variant="danger"
                v-if="events.includes('remove')">Remove</b-button>
      <b-button v-if="events.includes('upgrade')"
                variant="success"
                v-b-tooltip.hover
                title="Not implemented">Upgrade</b-button>
    </b-button-group>

    <b-modal v-if="card"
             :id="'info-modal'+card.id"
             :title="card.name"
             hide-footer>
      <div class="modal-body">
        <img class="modal-body__image"
             :src="getCardImage(card)"
             alt="card image">
        <div class="modal-body__info">
          <p class="card-description">{{card.description}}</p>
          <hr>
          <div class="card-info">
            <label>Type: </label>
            <p>{{card.type}}</p>
          </div>

          <div class="card-info">
            <label>Rarity: </label>
            <p>{{card.rarity}}</p>
          </div>

          <div class="card-info">
            <label>Elixir Cost: </label>
            <p>{{card.elixirCost}}</p>
          </div>

        </div>
      </div>
    </b-modal>
  </div>
</template>

<script>
import ClickOutside from "vue-click-outside";
export default {
  props: {
    card: Object,
    handleClickAction: Function
  },

  data() {
    return {
      cardImage: null,
      events: []
    };
  },

  computed: {
    isEmpty: function() {
      return !this.card;
    }
  },

  methods: {
    getCardImage: function(card) {
      return `http://www.clashapi.xyz/images/cards/${card.idName}.png`;
    },
    getPossibleEvents: function() {
      this.events = this.handleClickAction(this.card);
    },
    clearEvents: function() {
      this.events = [];
    },
    emitEvent: function(event, data = null) {
      this.$emit(event, data);
      this.clearEvents();
    }
  },

  watch: {
    events: {
      immediate: true,
      handler: function(newEvents) {
        const hasSelectEvent = newEvents.includes("select");
        const hasUseEvent = newEvents.includes("use");
        if (hasSelectEvent) this.emitEvent("select-mode");
        else if (hasUseEvent) this.emitEvent("use-card", this.card);
      }
    }
  },

  directives: {
    ClickOutside
  }
};
</script>

<style scoped>
.card {
  border: none;
}

.card:hover {
  cursor: pointer;
}

.card,
.card-image,
.card-image > img {
  width: 100%;
  height: 100%;
}

.card__elixir {
  position: absolute;
  top: 0;
  left: 0;
  color: white;
  background-color: #e960ec;
  border-radius: 50%;
  text-align: center;
  width: 100%;
  height: 100%;
  line-height: 33px;
}

.card.empty-card {
  border: 1px dashed grey;
}

.add-card-icon {
  margin: auto;
  color: grey;
  width: 50px;
  display: block;
  height: 100%;
}

.action-btns {
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 1;
}

.action-btns {
  width: 100%;
}

.action-btns > button {
  margin: 0;
  font-size: 0.9rem;
}

.modal-body {
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
}

.modal-body__image {
  flex-basis: 20%;
  width: 20%;
  height: 20%;
}

.modal-body__info {
  flex-basis: 75%;
  font-size: 0.9rem;
}

.card-info > * {
  display: inline-block;
}

.card-description {
  font-weight: 600;
}

.card-info > p {
  font-weight: 600;
  margin-left: 1%;
}
</style>
