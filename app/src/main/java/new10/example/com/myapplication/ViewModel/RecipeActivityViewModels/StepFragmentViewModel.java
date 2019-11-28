package new10.example.com.myapplication.ViewModel.RecipeActivityViewModels;

import androidx.lifecycle.ViewModel;

import java.util.List;

import new10.example.com.myapplication.Model.Step;

public class StepFragmentViewModel extends ViewModel {
    private List<Step> stepList;
    private int pos = -1;
    private Step step;

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    public int getPos() {
        return pos;
    }
    public void setPos(int pos) {
        this.pos = pos;
    }
    public void setStep(Step step){
        this.step = step;
    }
    public Step getStep(){

        return stepList.get(pos);
    }

}
