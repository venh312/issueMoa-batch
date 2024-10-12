package com.issuemoa.batch;

import java.util.ArrayList;
import java.util.List;

public class Notepad {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums);
        return result;
    }

    private void backtrack(List<List<Integer>> result, List<Integer> path, int[] nums) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int num : nums) {
            // 이미 path에 포함된 요소는 건너뜀
            if (path.contains(num)) continue;
            path.add(num);
            backtrack(result, path, nums);
            System.out.println("path.size() - 1 : " + (path.size() - 1));
            path.remove(path.size() - 1);  // 선택한 요소를 제거하여 다음 반복에 사용할 수 있게 함
        }
    }

    public static void main(String[] args) {
        Notepad notepad = new Notepad();
        System.out.println(notepad.permute(new int[]{1,2,3}));

    }
}
