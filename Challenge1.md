    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;

    public class TripletsSum {
    
        public static List<List<Integer>> findTriplets(int[] nums, int target) {
            List<List<Integer>> triplets = new ArrayList<>();
            
            if (nums == null || nums.length < 3)
                return triplets;
            
            Arrays.sort(nums);
            
            for (int i = 0; i < nums.length - 2; i++) {
                if (i == 0 || (i > 0 && nums[i] != nums[i - 1])) {
                    int left = i + 1, right = nums.length - 1, sum = target - nums[i];
                    while (left < right) {
                        if (nums[left] + nums[right] == sum) {
                            triplets.add(Arrays.asList(nums[i], nums[left], nums[right]));
                            while (left < right && nums[left] == nums[left + 1]) left++;
                            while (left < right && nums[right] == nums[right - 1]) right--;
                            left++;
                            right--;
                        } else if (nums[left] + nums[right] < sum) {
                            left++;
                        } else {
                            right--;
                        }
                    }
                }
            }
            
            return triplets;
        }
    }