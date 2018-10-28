classdef <decl>hit_list
    properties
        bounds;     % Nx4 array of single. [min_x min_y width height]
        poselet_id; % Nx1 array of uint16. Unique ID of the detected poselet
        score;      % Nx1 array of single. Score of the SVM classifier
        image_id;   % Nx1 array of uint64. The FBID of each image (or other uniqueid)
        cluster_id; % Nx1 array of uint16. The index of the hyp cluster within the image
        size;       % uint32 size
    end
    methods
        function h = hit_list(bounds,score,poselet_id,image_id,cluster_id)
            % constructor
        end
        function is=isempty(h)
            is=(h.size==0);
        end
    end
end

my_list = <ref>hit_list([], 0, 0, [], 0);
