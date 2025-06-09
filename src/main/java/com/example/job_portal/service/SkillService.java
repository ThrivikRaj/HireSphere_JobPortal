package com.example.job_portal.service;

import com.example.job_portal.dto.SkillDTO;
import com.example.job_portal.model.Skill;
import com.example.job_portal.repository.SkillRepository;
import com.example.job_portal.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private EntityMapper entityMapper;

    public List<SkillDTO> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(entityMapper::toSkillDTO)
                .collect(Collectors.toList());
    }

    public SkillDTO getSkillById(Long id) {
        return skillRepository.findById(id)
                .map(entityMapper::toSkillDTO)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
    }

    public SkillDTO createSkill(SkillDTO skillDTO) {
        Skill skill = new Skill();
        skill.setName(skillDTO.getName());
        skill.setCategory(skillDTO.getCategory());
        
        Skill savedSkill = skillRepository.save(skill);
        return entityMapper.toSkillDTO(savedSkill);
    }

    public SkillDTO updateSkill(Long id, SkillDTO skillDTO) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
        
        skill.setName(skillDTO.getName());
        skill.setCategory(skillDTO.getCategory());
        
        Skill updatedSkill = skillRepository.save(skill);
        return entityMapper.toSkillDTO(updatedSkill);
    }

    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new RuntimeException("Skill not found with id: " + id);
        }
        skillRepository.deleteById(id);
    }

    public Optional<SkillDTO> findByName(String name) {
        return skillRepository.findByName(name)
                .map(entityMapper::toSkillDTO);
    }

    public Optional<SkillDTO> findByNameAndCategory(String name, String category) {
        return skillRepository.findByNameAndCategory(name, category)
                .map(entityMapper::toSkillDTO);
    }
} 