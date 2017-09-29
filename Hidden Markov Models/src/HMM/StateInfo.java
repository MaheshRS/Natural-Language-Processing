package HMM;

public class StateInfo {
    public enum State {
        kStateHot,
        kStateCold,
    }

    private class StateObj {
        private State stateType;
        private State probableInfluenceStateType;
        public double estimatedMLE;

        public StateObj (State stateType) {
            this.stateType = stateType;
        }
    }

    StateObj hotState = new StateObj(State.kStateHot);
    StateObj coldState = new StateObj(State.kStateCold);

    public void setStateMLE (double hotStateMLE, double coldStateMLE) {
        this.hotState.estimatedMLE = hotStateMLE;
        this.coldState.estimatedMLE = coldStateMLE;
    }

    public void setInfluencialStateType (State hotStateInfluencer, State coldStateInfluencer) {
        this.hotState.probableInfluenceStateType = hotStateInfluencer;
        this.coldState.probableInfluenceStateType = coldStateInfluencer;
    }

    public State getInfluencer (State state) {
        if (state == State.kStateHot) {
            return this.hotState.probableInfluenceStateType;
        }
        else {
            return this.coldState.probableInfluenceStateType;
        }
    }
}
