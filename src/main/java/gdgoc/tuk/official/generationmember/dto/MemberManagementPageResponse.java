package gdgoc.tuk.official.generationmember.dto;

import java.util.List;

public record MemberManagementPageResponse(
        int totalPage, int currentPage, List<MemberManagementResponse> memberManagementResponses) {}
